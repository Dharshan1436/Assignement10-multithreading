package assignment10.garbageCollection;

class GCExample {
    public GCExample() {
        System.out.println("Object created: " + this);
    }

    @Override
    protected void finalize() {
        System.out.println("Garbage collected: " + this);
    }
}

public class Program1 {
    public static void main(String[] args) {
        System.out.println("Creating GCExample objects...");
        for (int i = 0; i < 5; i++) {
            GCExample obj = new GCExample();
            obj = null; 
        }

        System.out.println("Requesting garbage collection...");
        System.gc(); 

       
        try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Main method finished execution.");
    }
}
