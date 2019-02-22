package rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * ���ѷ���
 * @author drake
 *
 */
public class ClientMain {
	public static void main(String[] args) {

		HelloService helloService = (HelloService) getObject(HelloService.class);
		System.out.println("---�õ��˶���׼��ʵ��---");
		System.out.println("result: " + helloService.say("Hello Drake"));
	}
	static String address = "localhost";
	static int port = 20880;
	/**
	 * Զ�̷������ã��õ�
	 * @param clazz ����Ľӿ�
	 * @return ���õķ����ķ���ֵ
	 */
	public static Object getObject(Class clazz)
	{
		return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
			/**
			 * ��������
			 * @method: ����ķ���
			 * @args: ����
			 */
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				Socket socket = new Socket(address, port);
				System.out.println("!!!��ȡ����: ");
				String className = clazz.getName(); // ����
				String methodName = method.getName(); // ������
				Class[] types = method.getParameterTypes(); // ��������
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
				// ������������������������������������ͣ��;������(Object����)
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
