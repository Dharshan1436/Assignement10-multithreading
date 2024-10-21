package assignment10.multithreading;

class Singleton {
    private static volatile Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {  
            synchronized (Singleton.class) {  
                if (instance == null) {  
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}

public class Program3 {
    public static void main(String[] args) {
        
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();

        System.out.println("Instance 1 hashcode: " + instance1.hashCode());
        System.out.println("Instance 2 hashcode: " + instance2.hashCode());

       
        System.out.println("Are both instances equal? " + (instance1 == instance2));
    }
}
