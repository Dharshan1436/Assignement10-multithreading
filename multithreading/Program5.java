package assignment10.multithreading;

import java.util.concurrent.Semaphore;

class Chopstick {
	private final Semaphore semaphore;

	public Chopstick() {
		semaphore = new Semaphore(1);
	}

	public void pickUp() throws InterruptedException {
		semaphore.acquire();
	}

	public void putDown() {
		semaphore.release();
	}
}

class Philosopher extends Thread {
	private final int id;
	private final Chopstick leftChopstick;
	private final Chopstick rightChopstick;

	public Philosopher(int id, Chopstick leftChopstick, Chopstick rightChopstick) {
		this.id = id;
		this.leftChopstick = leftChopstick;
		this.rightChopstick = rightChopstick;
	}

	@Override
	public void run() {
		try {
			while (true) {
				think();
				eat();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void think() throws InterruptedException {
		System.out.println("Philosopher " + id + " is thinking.");
		Thread.sleep((long) (Math.random() * 1000));
	}

	private void eat() throws InterruptedException {
		System.out.println("Philosopher " + id + " is hungry.");

		leftChopstick.pickUp();
		rightChopstick.pickUp();

		System.out.println("Philosopher " + id + " is eating.");
		Thread.sleep((long) (Math.random() * 1000));

		leftChopstick.putDown();
		rightChopstick.putDown();
	}
}

public class Program5 {
	public static void main(String[] args) {
		final int NUM_PHILOSOPHERS = 5;
		Chopstick[] chopsticks = new Chopstick[NUM_PHILOSOPHERS];
		Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];

		for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
			chopsticks[i] = new Chopstick();
		}

		for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
			philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % NUM_PHILOSOPHERS]);
			philosophers[i].start();
		}
	}
}
