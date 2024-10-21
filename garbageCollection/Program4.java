package assignment10.garbageCollection;

import java.util.concurrent.ArrayBlockingQueue;

class SharedBuffer {
    private final ArrayBlockingQueue<Integer> buffer;

    public SharedBuffer(int capacity) {
        buffer = new ArrayBlockingQueue<>(capacity);
    }

    public void produce(int item) throws InterruptedException {
        buffer.put(item); 
        System.out.println("Produced: " + item);
    }

    public int consume() throws InterruptedException {
        int item = buffer.take(); 
        System.out.println("Consumed: " + item);
        return item;
    }
}

class Producer implements Runnable {
    private final SharedBuffer sharedBuffer;

    public Producer(SharedBuffer sharedBuffer) {
        this.sharedBuffer = sharedBuffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) { 
            try {
                sharedBuffer.produce(i);
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer interrupted.");
            }
        }
    }
}

class Consumer implements Runnable {
    private final SharedBuffer sharedBuffer;

    public Consumer(SharedBuffer sharedBuffer) {
        this.sharedBuffer = sharedBuffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                sharedBuffer.consume();
                Thread.sleep(150); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer interrupted.");
            }
        }
    }
}

public class Program4 {
    public static void main(String[] args) {
        SharedBuffer sharedBuffer = new SharedBuffer(5);

        Thread producerThread = new Thread(new Producer(sharedBuffer));
        Thread consumerThread = new Thread(new Consumer(sharedBuffer));

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted.");
        }

        System.out.println("Main thread finished execution.");
    }
}
