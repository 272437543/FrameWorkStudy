package message_queue_remote;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class MQpublisher {

	public static void main(String[] args) {
		new MQpublisher();
	}
	public MQpublisher() {
		publishQueue(9092);
	}
	/**
	 * 一个端口对应一个队列
	 * @param port
	 */
	public void publishQueue(int port)
	{
		MyMessageQueue queue = new MyMessageQueue(); // 创建一个新队列
		System.out.println("Queue Published on " + port);
		try
		{
			ServerSocket ss = new ServerSocket(port);
			while (true)
			{
				Socket socket = ss.accept(); // 等待连接
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				String methodName = ois.readUTF(); // 读取请求的方法名
				Class[] types = (Class[]) ois.readObject(); // 读取类型
				Object[] args = (Object[]) ois.readObject(); // 读取参数
				Class clazz = MyMessageQueue.class;
				
				Method method = clazz.getMethod(methodName, types);
				Object ret = method.invoke(queue, args); // 执行方法
				System.out.println(ret);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(ret); // 写入输出流
				oos.flush();
				
				ois.close();
				oos.close();
				socket.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
