package message_queue_remote;

public interface MessageQueue {
	public String post(String msg);
	public String nextMessage();
	public int size();
}
