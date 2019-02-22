package message_queue;

import java.util.Random;

public class MQmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MQmain().fun();
	}
	public void fun() {
		MyMessageQueue myMQ = new MyMessageQueue();

//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while (true) {
//					MyMessage msg = new MyMessage();
//
//					msg.setContent("drake AAA-" + new Random().nextInt(1000));
//					myMQ.post(msg);
//
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					MyMessage msg = new MyMessage();

					msg.setContent("drake BBB-" + new Random().nextInt(1000));
					System.out.println("Posting: " + msg);
					myMQ.post(msg);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();

		receive(myMQ);
	}

	public void receive(MyMessageQueue myMQ) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					MyMessage next = myMQ.nextMessage();
					if (next == null)
						continue;
					// System.out.println("Size: " + myMQ.size());
					System.out.println(Thread.currentThread().getId()
							+ " has received: " + next.getContent());
					// if (next.getRunnable() != null) next.getRunnable().run();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
