package message_queue_remote;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class MQclient {
	public static void main(String[] args) {
		new MQclient();
	}

	public MQclient() {
		MessageQueue queue = (MessageQueue) queueProxy(MessageQueue.class);
		String result = queue.post("drake-1");
		System.out.println(result);
		String result1 = queue.post("drake-2");
		System.out.println(result1);
		System.out.println("size: " + queue.size());
		String msg = queue.nextMessage();
		System.out.println("get: " + msg);
		String msg1 = queue.nextMessage();
		System.out.println("get: " + msg1);
		String msg2 = queue.nextMessage();
		System.out.println("get: " + msg2);
	}
	static String address = "localhost";
	static int port = 9092;
	/**
	 * 
	 * @param clazz 队列的接口
	 * @return
	 */
	public Object queueProxy(Class clazz) {
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
						oos.writeUTF(methodName); // 写入方法名
						oos.writeObject(types); // 写入参数类型
						oos.writeObject(args); // 写入参数
						
						oos.flush();

						ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
						Object ret = ois.readObject(); // 得到响应
						// System.out.println("return: " + ret);
						oos.close();
						ois.close();
						socket.close();
						return ret;
					}
				});
	}
}
