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
	 * 队列策略
	 * @param type 策略类型或参数
	 */
	String strategy(String type)
	{
		// TODO 安排队列策略（例：优先队列）
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
