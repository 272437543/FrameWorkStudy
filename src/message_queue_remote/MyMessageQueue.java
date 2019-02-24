package message_queue_remote;

import java.util.ArrayDeque;
import java.util.Queue;

public class MyMessageQueue implements MessageQueue{
	private final Queue<String> queue = new ArrayDeque<String>();
	@Override
	public String post(String msg) {
		//System.out.println("POSTING!");
		String ret = "added:";
		try{
			synchronized (this) {
				//System.out.println("Posting..." + strategy("None"));
				queue.add(msg);
				ret += msg;
				ret += strategy("None");
			}
		}catch(Exception e)
		{
			ret = "fail";
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * ���в���
	 * @param type �������ͻ����
	 */
	String strategy(String type)
	{
		// TODO ���Ŷ��в��ԣ��������ȶ��У�
		return "[strategy: " + type + "]";
	}
	@Override
	public int size()
	{
		return queue.size();
	}

	@Override
	public String nextMessage() {
		//System.out.println("GETING!");
		synchronized (this) {
			// System.out.println("returning...");
			if (!queue.isEmpty()) {
				return queue.poll();
			}else {
				return "null";
			}
			// wait();
		}
	}
}
