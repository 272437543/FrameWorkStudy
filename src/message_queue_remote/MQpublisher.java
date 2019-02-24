package message_queue_remote;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import message_queue.MyMessageQueue;

public class MQpublisher {

	public static void main(String[] args) {
		new MQpublisher();
	}
	public MQpublisher() {
		publishQueue(9092);
	}
	
	public void publishQueue(int port)
	{
		System.out.println("Publish on " + port);
		MyMessageQueue queue = new MyMessageQueue(); // 创建一个新队列
		try
		{
			ServerSocket ss = new ServerSocket(port);
			while (true)
			{
				Socket socket = ss.accept(); // 等待连接
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				String methodName = ois.readUTF(); // 得到请求的方法名
				Class[] types = (Class[]) ois.readObject();
				Object[] args = (Object[]) ois.readObject();
				Class clazz = MyMessageQueue.class;
				
				Method method = clazz.getMethod(methodName, types);
				Object ret = method.invoke(queue, args);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(ret);
				oos.flush();
				
				ois.close();
				oos.close();
				socket.close();
			}
		}
		catch(Exception e)
		{
			
		}
	}
}
