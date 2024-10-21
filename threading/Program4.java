package assignment10.threading;

import java.util.concurrent.ConcurrentHashMap;

class ThreadSafeDictionary<K, V> {
    private final ConcurrentHashMap<K, V> map;

    public ThreadSafeDictionary() {
        this.map = new ConcurrentHashMap<>();
    }

    public void put(K key, V value) {
        map.put(key, value);
    }

    public V get(K key) {
        return map.get(key);
    }

    public void remove(K key) {
        map.remove(key);
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public int size() {
        return map.size();
    }
}

class DictionaryTester implements Runnable {
    private final ThreadSafeDictionary<String, Integer> dictionary;

    public DictionaryTester(ThreadSafeDictionary<String, Integer> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            String key = "Key" + i;
            dictionary.put(key, i);
            System.out.println(Thread.currentThread().getName() + " added: " + key + " = " + i);
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class Program4 {
    public static void main(String[] args) {
        ThreadSafeDictionary<String, Integer> dictionary = new ThreadSafeDictionary<>();

        Thread thread1 = new Thread(new DictionaryTester(dictionary), "Thread-1");
        Thread thread2 = new Thread(new DictionaryTester(dictionary), "Thread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final size of the dictionary: " + dictionary.size());
    }
}
