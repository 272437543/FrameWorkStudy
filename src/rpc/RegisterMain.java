package rpc;

/**
 * ��������
 * @author drake
 *
 */
public class RegisterMain {

	public static void main(String[] args) {
		// �����ķ�����
		Register register = new Register();
		// �����˿�
		register.publisher(HelloServiceImpl.class, 20880);
	}

}
