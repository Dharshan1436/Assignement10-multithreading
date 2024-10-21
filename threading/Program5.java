package assignment10.threading;

class ThreadLocalExample {
    
    private static final ThreadLocal<Integer> threadLocalVariable = ThreadLocal.withInitial(() -> 0);

    public void increment() {
  
        int currentValue = threadLocalVariable.get();
        currentValue++;
        threadLocalVariable.set(currentValue);
        System.out.println(Thread.currentThread().getName() + ": " + currentValue);
    }

    public void printValue() {
        System.out.println(Thread.currentThread().getName() + ": " + threadLocalVariable.get());
    }
}

class WorkerThread implements Runnable {
    private final ThreadLocalExample example;

    public WorkerThread(ThreadLocalExample example) {
        this.example = example;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            example.increment();
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        example.printValue();
    }
}

public class Program5 {
    public static void main(String[] args) {
        ThreadLocalExample example = new ThreadLocalExample();

        Thread thread1 = new Thread(new WorkerThread(example), "Thread-1");
        Thread thread2 = new Thread(new WorkerThread(example), "Thread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Main thread finished execution.");
    }
}
