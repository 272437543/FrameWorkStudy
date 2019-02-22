package rpc;

/**
 * 实现HelloService的类
 * @author drake
 *
 */
public class HelloServiceImpl implements HelloService{
	public String say(String msg)
	{
		return "saying: " + msg;
	}
	
	public int count(String str)
	{
		return str.length();
	}
}
