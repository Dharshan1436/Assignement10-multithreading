package assignment10.threading;

class Task implements Runnable {
	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				System.out.println("Task is running...");
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			System.out.println("Task was interrupted during sleep.");
		} finally {
			System.out.println("Cleaning up resources...");
		}
	}
}

public class Program2 {
	public static void main(String[] args) {
		Thread taskThread = new Thread(new Task());
		taskThread.start();

		try {

			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Main thread is interrupting the task thread.");
		taskThread.interrupt();

		try {

			taskThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Main thread has finished execution.");
	}
}
