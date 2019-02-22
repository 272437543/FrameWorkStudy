package rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ע������
 * @author drake
 *
 */
public class Register {
	/**
	 * ��������
	 * @param serviceImpl ����ʵ����
	 * @param port �˿�
	 */
	public void publisher(Class serviceImpl, int port)
	{
		System.out.println("register: " + serviceImpl + " on " + "localhost" + ":" + port);
		try {
			ServerSocket ss = new ServerSocket(port);
			// ����һ��ServerSocket
			while (true)
			{
				Socket socket = ss.accept(); // �ȴ����ӣ���û�������̻߳����ȴ�
				System.out.println("~~~�õ�����: ");
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				
				String className = ois.readUTF();
				String methodName = ois.readUTF();
				Class[] types = (Class[]) ois.readObject();
				Object[] methodArgs = (Object[]) ois.readObject();
				// ��ȡ�õ���Ӧ����
				System.out.println("className: " + className);
				System.out.println("methodName: " + methodName);
				for (int i=0; i<methodArgs.length; i++)
				{
					System.out.println("param: " + types[i] + " = " + methodArgs[i]);
				}
				Class clazz = serviceImpl;
				// �õ����巽��
				Method method = clazz.getMethod(methodName, types);
				System.out.println("method: " + method.getName());
				// ͨ����������õ����巽���ķ���ֵ
				Object invoke = method.invoke(clazz.newInstance(), methodArgs);
				System.out.println("invoke: " + invoke);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(invoke);
				// �����ͨ����������ݵ�������
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
