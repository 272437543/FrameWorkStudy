package message_queue;

public class MyMessage {

	private String content;
	public MyMessage()
	{
		
	}
	public MyMessage(String content)
	{
		this.content = content;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "MyMessage [content=" + content + "]";
	}
}
	
