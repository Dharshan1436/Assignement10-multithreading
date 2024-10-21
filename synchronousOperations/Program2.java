package assignment10.synchronousOperations;

import java.util.concurrent.CountDownLatch;

class Task implements Runnable {
    private final CountDownLatch latch;
    private final String taskName;

    public Task(CountDownLatch latch, String taskName) {
        this.latch = latch;
        this.taskName = taskName;
    }

    @Override
    public void run() {
        try {
            System.out.println(taskName + " is starting.");
            Thread.sleep((int) (Math.random() * 1000)); 
            System.out.println(taskName + " is finished.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            latch.countDown();
        }
    }
}

public class Program2 {
    public static void main(String[] args) {
        final int numberOfTasks = 3;
        CountDownLatch latch = new CountDownLatch(numberOfTasks);

        for (int i = 1; i <= numberOfTasks; i++) {
            Thread taskThread = new Thread(new Task(latch, "Task " + i));
            taskThread.start();
        }

        try {
            latch.await(); 
            System.out.println("All tasks are finished.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
