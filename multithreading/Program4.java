package assignment10.multithreading;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Program4 {
	private static final int MAX_SIZE = 5;
	private final Queue<Integer> queue = new LinkedList<>();
	private final Semaphore empty = new Semaphore(MAX_SIZE);
	private final Semaphore full = new Semaphore(0);

	class Producer extends Thread {
		public void run() {
			try {
				for (int i = 0; i < 20; i++) {
					empty.acquire();
					produce(i);
					full.release();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void produce(int value) {
			synchronized (queue) {
				queue.add(value);
				System.out.println("Produced: " + value);
			}
		}
	}

	class Consumer extends Thread {
		public void run() {
			try {
				for (int i = 0; i < 20; i++) {
					full.acquire();
					consume();
					empty.release();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void consume() {
			synchronized (queue) {
				int value = queue.poll();
				System.out.println("Consumed: " + value);
			}
		}
	}

	public void start() {
		Producer producer = new Producer();
		Consumer consumer = new Consumer();
		producer.start();
		consumer.start();
	}

	public static void main(String[] args) {
		Program4 pc = new Program4();
		pc.start();
	}
}
