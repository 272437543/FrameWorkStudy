package rpc;

/**
 * 发布服务
 * @author drake
 *
 */
public class RegisterMain {

	public static void main(String[] args) {
		// 发布的服务类
		Register register = new Register();
		// 发布端口
		register.publisher(HelloServiceImpl.class, 20880);
	}

}
