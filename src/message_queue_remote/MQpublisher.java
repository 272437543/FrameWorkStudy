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
		MyMessageQueue queue = new MyMessageQueue(); // 创建一个新队列
		try
		{
			ServerSocket ss = new ServerSocket(port);
			while (true)
			{
				Socket socket = ss.accept(); // 等待连接
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				String methodName = ois.readUTF(); // 得到请求的方法名
				String ret = "";
				if (methodName.equals("post"))
				{
					// 加入队列
					//System.out.println("加入队列");
					Object[] args = (Object[]) ois.readObject();
					//System.out.println(args[0]);
					ret = queue.post((String) args[0]);
				}else
				if (methodName.equals("nextMessage"))
				{
					// 弹出队列
					//System.out.println("弹出队列");
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
