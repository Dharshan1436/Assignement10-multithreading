package assignment10.synchronousOperations;


import java.util.concurrent.CopyOnWriteArrayList;

class SynchronousListExample {
    private final CopyOnWriteArrayList<String> list;

    public SynchronousListExample() {
        this.list = new CopyOnWriteArrayList<>();
    }

    public void addValue(String value) {
        list.add(value);
        System.out.println("Inserted: " + value);
    }

    public void getValue(int index) {
        if (index < list.size()) {
            String value = list.get(index);
            System.out.println("Fetched from index " + index + ": " + value);
        } else {
            System.out.println("Index " + index + " is out of bounds.");
        }
    }
}

class ListTask implements Runnable {
    private final SynchronousListExample example;
    private final String value;
    private final int index;

    public ListTask(SynchronousListExample example, String value, int index) {
        this.example = example;
        this.value = value;
        this.index = index;
    }

    @Override
    public void run() {
        example.addValue(value);
        example.getValue(index);
    }
}

public class Program4 {
    public static void main(String[] args) {
        SynchronousListExample example = new SynchronousListExample();

       
        Thread thread1 = new Thread(new ListTask(example, "One", 0));
        Thread thread2 = new Thread(new ListTask(example, "Two", 1));
        Thread thread3 = new Thread(new ListTask(example, "Three", 2));
        Thread thread4 = new Thread(new ListTask(example, "Uno", 0)); 
        Thread thread5 = new Thread(new ListTask(example, "Dos", 1)); 

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

        System.out.println("All list operations completed.");
    }
}
