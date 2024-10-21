package assignment10.synchronousOperations;

import java.util.concurrent.ConcurrentHashMap;

class SynchronousMapExample {
    private final ConcurrentHashMap<Integer, String> map;

    public SynchronousMapExample() {
        this.map = new ConcurrentHashMap<>();
    }

    public void putValue(Integer key, String value) {
        map.put(key, value);
        System.out.println("Inserted: " + key + " => " + value);
    }

    public void getValue(Integer key) {
        String value = map.get(key);
        System.out.println("Fetched: " + key + " => " + value);
    }
}

class MapTask implements Runnable {
    private final SynchronousMapExample example;
    private final Integer key;
    private final String value;

    public MapTask(SynchronousMapExample example, Integer key, String value) {
        this.example = example;
        this.key = key;
        this.value = value;
    }

    @Override
    public void run() {
        example.putValue(key, value);
        example.getValue(key);
    }
}

public class Program3 {
    public static void main(String[] args) {
        SynchronousMapExample example = new SynchronousMapExample();

        
        Thread thread1 = new Thread(new MapTask(example, 1, "One"));
        Thread thread2 = new Thread(new MapTask(example, 2, "Two"));
        Thread thread3 = new Thread(new MapTask(example, 3, "Three"));
        Thread thread4 = new Thread(new MapTask(example, 1, "Uno")); 
        Thread thread5 = new Thread(new MapTask(example, 2, "Dos")); 

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("All map operations completed.");
    }
}
