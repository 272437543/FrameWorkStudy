package rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * 消费服务
 * @author drake
 *
 */
public class ClientMain {
	public static void main(String[] args) {

		HelloService helloService = (HelloService) getObject(HelloService.class);
		System.out.println("---得到了对象，准备实现---");
		System.out.println("result: " + helloService.say("Hello Drake"));
	}
	static String address = "localhost";
	static int port = 20880;
	/**
	 * 远程方法调用，得到
	 * @param clazz 传入的接口
	 * @return 调用的方法的返回值
	 */
	public static Object getObject(Class clazz)
	{
		return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
			/**
			 * 方法代理
			 * @method: 代理的方法
			 * @args: 参数
			 */
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				Socket socket = new Socket(address, port);
				System.out.println("!!!获取对象: ");
				String className = clazz.getName(); // 类名
				String methodName = method.getName(); // 方法名
				Class[] types = method.getParameterTypes(); // 参数类型
				System.out.println("class: " + className);
				System.out.println("method: " + methodName);
				for (int i=0; i<args.length; i++)
				{
					System.out.println("param: " + types[i] + " = " + args[i]);
				}
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeUTF(className);
				oos.writeUTF(methodName);
				
				oos.writeObject(types);
				oos.writeObject(args);
				// 在输出流中添加类名，方法名，传入参数类型，和具体参数(Object类型)
				oos.flush();
				
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				Object ret = ois.readObject();
				
				oos.close();
				ois.close();
				socket.close();
				return ret;
			}
		});
	}
}
