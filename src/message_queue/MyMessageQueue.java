package message_queue;

import java.util.ArrayDeque;
import java.util.Queue;

public class MyMessageQueue {
	private final Queue<MyMessage> queue = new ArrayDeque<MyMessage>();
	public void post(MyMessage msg) {
		synchronized (this) {
			//System.out.println("Posting...");
			notify();
			queue.add(msg);
		}
	}
	
	public int size()
	{
		return queue.size();
	}

	public MyMessage nextMessage() {
		while (true) {

			try {
				synchronized (this) {
					//System.out.println("returning...");
					if (!queue.isEmpty()) {
						return queue.poll();
					}
					wait();
					
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
