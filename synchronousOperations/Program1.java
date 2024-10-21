package assignment10.synchronousOperations;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Program1 {
    private static final int CAPACITY = 5; 
    private final BlockingQueue<Integer> queue;

    public Program1() {
        this.queue = new ArrayBlockingQueue<>(CAPACITY);
    }

    public void produce() {
        try {
            for (int i = 1; i <= 10; i++) {
                queue.put(i); 
                System.out.println("Produced: " + i);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void consume() {
        try {
            for (int i = 1; i <= 10; i++) {
                int value = queue.take(); 
                System.out.println("Consumed: " + value);
                Thread.sleep(300); 
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        Program1 example = new Program1();

        Thread producerThread = new Thread(example::produce, "Producer");
        Thread consumerThread = new Thread(example::consume, "Consumer");

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Production and consumption finished.");
    }
}
