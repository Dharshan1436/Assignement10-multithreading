package assignment10.multithreading;

public class Program2 {

    static class Resource {
        private final String name;

        public Resource(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public synchronized void use(Resource other)  {
            System.out.println(Thread.currentThread().getName() + " is using " + this.name);
            try {
                Thread.sleep(100);  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is trying to use " + other.getName());

        }
    }

    static class DeadlockThread extends Thread {
        private final Resource resource1;
        private final Resource resource2;

        public DeadlockThread(Resource resource1, Resource resource2) {
            this.resource1 = resource1;
            this.resource2 = resource2;
        }

        public void run() {
            try {
				resource1.use(resource2);
			} catch (Throwable e) {
				
				e.printStackTrace();
			}
        }
    }

    static class LivelockThread extends Thread {
        private final Resource resource1;
        private final Resource resource2;

        public LivelockThread(Resource resource1, Resource resource2) {
            this.resource1 = resource1;
            this.resource2 = resource2;
        }

        public void run() {
            while (true) {
                synchronized (resource1) {
                    System.out.println(Thread.currentThread().getName() + " has acquired " + resource1.getName());
                    synchronized (resource2) {
                        System.out.println(Thread.currentThread().getName() + " has acquired " + resource2.getName());
                        System.out.println(Thread.currentThread().getName() + " is working with resources.");
                        return;  
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        
        Resource resourceA = new Resource("Resource A");
        Resource resourceB = new Resource("Resource B");

        DeadlockThread thread1 = new DeadlockThread(resourceA, resourceB);
        DeadlockThread thread2 = new DeadlockThread(resourceB, resourceA);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Switching to Livelock demonstration ---\n");

     
        LivelockThread livelockThread1 = new LivelockThread(resourceA, resourceB);
        LivelockThread livelockThread2 = new LivelockThread(resourceB, resourceA);

        livelockThread1.start();
        livelockThread2.start();
    }
}
