package assignment10.synchronousOperations;

import java.util.concurrent.CyclicBarrier;

class Task1 implements Runnable {
    private final CyclicBarrier barrier;
    private final String threadName;

    public Task1(CyclicBarrier barrier, String threadName) {
        this.barrier = barrier;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        try {
            System.out.println(threadName + " is performing a task.");
            // Simulating some work with sleep
            Thread.sleep((int) (Math.random() * 1000));
            System.out.println(threadName + " has completed the task and is waiting at the barrier.");
            barrier.await(); // Wait at the barrier
            System.out.println(threadName + " has crossed the barrier and is continuing.");
        } catch (InterruptedException | java.util.concurrent.BrokenBarrierException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Program5 {
    public static void main(String[] args) {
        final int numberOfThreads = 3;
        CyclicBarrier barrier = new CyclicBarrier(numberOfThreads, () -> {
            System.out.println("All tasks are completed. Barrier is reset.");
        });

        for (int i = 1; i <= numberOfThreads; i++) {
            Thread thread = new Thread(new Task1(barrier, "Thread " + i));
            thread.start();
        }
    }
}
