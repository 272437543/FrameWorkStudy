package message_queue_remote;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
		MyMessageQueue queue = new MyMessageQueue(); // ����һ���¶���
		try
		{
			ServerSocket ss = new ServerSocket(port);
			while (true)
			{
				Socket socket = ss.accept(); // �ȴ�����
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				String methodName = ois.readUTF(); // �õ�����ķ�����
				String ret = "";
				if (methodName.equals("post"))
				{
					// �������
					//System.out.println("�������");
					Object[] args = (Object[]) ois.readObject();
					//System.out.println(args[0]);
					ret = queue.post((String) args[0]);
				}else
				if (methodName.equals("nextMessage"))
				{
					// ��������
					//System.out.println("��������");
					ret = queue.nextMessage();
				}else
				if (methodName.equals("size"))
				{
					ret = queue.size();
				}
				//System.out.println("client: " + ret);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeUTF(ret);
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
