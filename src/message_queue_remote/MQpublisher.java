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
	 * һ���˿ڶ�Ӧһ������
	 * @param port
	 */
	public void publishQueue(int port)
	{
		MyMessageQueue queue = new MyMessageQueue(); // ����һ���¶���
		System.out.println("Queue Published on " + port);
		try
		{
			ServerSocket ss = new ServerSocket(port);
			while (true)
			{
				Socket socket = ss.accept(); // �ȴ�����
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				String methodName = ois.readUTF(); // ��ȡ����ķ�����
				Class[] types = (Class[]) ois.readObject(); // ��ȡ����
				Object[] args = (Object[]) ois.readObject(); // ��ȡ����
				Class clazz = MyMessageQueue.class;
				
				Method method = clazz.getMethod(methodName, types);
				Object ret = method.invoke(queue, args); // ִ�з���
				System.out.println(ret);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(ret); // д�������
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
