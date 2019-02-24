package message_queue_remote;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

import message_queue.MessageQueue;

public class MQclient {
	public static void main(String[] args) {
		new MQclient();
	}

	public MQclient() {
		MessageQueue queue = (MessageQueue) queueProxy();
		String result = queue.post("drake 2");
		String result1 = queue.post("drake 3");
		System.out.println(result + " " + result1);
		System.out.println(queue.size());
		String msg = queue.nextMessage();
		System.out.println("get: " + msg);
	}
	static String address = "localhost";
	static int port = 9092;

	public Object queueProxy() {
		Class<MessageQueue> clazz = MessageQueue.class;
		return Proxy.newProxyInstance(clazz.getClassLoader(),
				new Class[] { clazz }, new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						Socket socket = new Socket(address, port);
						ObjectOutputStream oos = new ObjectOutputStream(socket
								.getOutputStream());
						String methodName = method.getName();
						// System.out.println(methodName + " - " + args);
						Class[] types = method.getParameterTypes();
						oos.writeUTF(methodName);
						oos.writeObject(types);
						oos.writeObject(args);
						
						oos.flush();

						ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
						Object ret = ois.readObject();
						// System.out.println("return: " + ret);
						oos.close();
						ois.close();
						socket.close();
						return ret;
					}
				});
	}
}
