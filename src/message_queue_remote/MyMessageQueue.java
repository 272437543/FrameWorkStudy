package message_queue_remote;

import java.util.ArrayDeque;
import java.util.Queue;

public class MyMessageQueue implements MessageQueue{
	private final Queue<String> queue = new ArrayDeque<String>();
	@Override
	public String post(String msg) {
		String ret = "added";
		try{
			synchronized (this) {
				//System.out.println("Posting...");
				// notify();
				queue.add(msg);
			}
		}catch(Exception e)
		{
			ret = "fail";
			e.printStackTrace();
		}
		return ret;
	}
	@Override
	public String size()
	{
		return "" + queue.size();
	}

	@Override
	public String nextMessage() {
		while (true) {

			synchronized (this) {
				//System.out.println("returning...");
				if (!queue.isEmpty()) {
					return queue.poll();
				}else {
					return "null";
				}
				// wait();
			}
		}
	}
}
