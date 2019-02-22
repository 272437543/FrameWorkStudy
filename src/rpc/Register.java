package rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 注册中心
 * @author drake
 *
 */
public class Register {
	/**
	 * 发布服务
	 * @param serviceImpl 具体实现类
	 * @param port 端口
	 */
	public void publisher(Class serviceImpl, int port)
	{
		System.out.println("register: " + serviceImpl + " on " + "localhost" + ":" + port);
		try {
			ServerSocket ss = new ServerSocket(port);
			// 建立一个ServerSocket
			while (true)
			{
				Socket socket = ss.accept(); // 等待连接，若没有连接线程会进入等待
				System.out.println("~~~得到请求: ");
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				
				String className = ois.readUTF();
				String methodName = ois.readUTF();
				Class[] types = (Class[]) ois.readObject();
				Object[] methodArgs = (Object[]) ois.readObject();
				// 读取得到相应参数
				System.out.println("className: " + className);
				System.out.println("methodName: " + methodName);
				for (int i=0; i<methodArgs.length; i++)
				{
					System.out.println("param: " + types[i] + " = " + methodArgs[i]);
				}
				Class clazz = serviceImpl;
				// 得到具体方法
				Method method = clazz.getMethod(methodName, types);
				System.out.println("method: " + method.getName());
				// 通过输入参数得到具体方法的返回值
				Object invoke = method.invoke(clazz.newInstance(), methodArgs);
				System.out.println("invoke: " + invoke);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(invoke);
				// 将结果通过输出流传递到消费者
				oos.flush();
				
				ois.close();
				oos.close();
				socket.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
