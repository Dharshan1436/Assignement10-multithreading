package assignment10.threading;

import java.util.LinkedList;

class ThreadSafeQueue<T> {
    private final LinkedList<T> queue;
    private final int capacity;

    public ThreadSafeQueue(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public synchronized void enqueue(T item) throws InterruptedException {
        while (queue.size() == capacity) {
            wait(); 
        }
        queue.add(item);
        notifyAll(); 
    }

    public synchronized T dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); 
        }
        T item = queue.removeFirst();
        notifyAll(); 
        return item;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized int size() {
        return queue.size();
    }
}

class Producer1 implements Runnable {
    private final ThreadSafeQueue<Integer> queue;

    public Producer1(ThreadSafeQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("Producing: " + i);
                queue.enqueue(i);
                Thread.sleep(500); 
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer1 implements Runnable {
    private final ThreadSafeQueue<Integer> queue;

    public Consumer1(ThreadSafeQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                Integer value = queue.dequeue();
                System.out.println("Consuming: " + value);
                Thread.sleep(1000); 
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Program3 {
    public static void main(String[] args) {
        ThreadSafeQueue<Integer> queue = new ThreadSafeQueue<>(5); 

        Thread producerThread = new Thread(new Producer1(queue));
        Thread consumerThread = new Thread(new Consumer1(queue));

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
