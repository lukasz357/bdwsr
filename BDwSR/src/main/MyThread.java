package main;

public class MyThread implements Runnable {

	private int	priority	= 0;
	private int	number		= 0;
	private int	DELAY		= 2000;

	public MyThread(int priority, int number) {
		this.priority = priority;
		this.number = number;
	}

	public void run() {
		try {
			while (true) {
				MainFrame.getInstance().getTextArea().append("Wątek " + number + " o priorytecie: " + priority + " zgłasza się!\n");
				Thread.sleep(DELAY);
			}
		} catch (Exception exception) {
			System.out.println("EXCEPTION");
		}
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
