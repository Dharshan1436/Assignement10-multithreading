package assignment10.garbageCollection;

import java.util.ArrayList;
import java.util.List;

class LargeData {
    private int[] largeArray;

    public LargeData(int size) {
        largeArray = new int[size]; 
        System.out.println("LargeData object created with array size: " + size);
    }
}

public class Program2 {
    public static void main(String[] args) {
        List<LargeData> dataList = new ArrayList<>();
        int initialSize = 5; 
        int arraySize = 10000000; 

        
        printMemoryUsage("Before creating LargeData objects:");

       
        for (int i = 0; i < initialSize; i++) {
            dataList.add(new LargeData(arraySize));
        }

        
        printMemoryUsage("After creating LargeData objects:");

        
        dataList.clear();
        System.gc(); 

   
        printMemoryUsage("After clearing the list and requesting garbage collection:");

        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Main method finished execution.");
    }

    private static void printMemoryUsage(String message) {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        System.out.println(message);
        System.out.println("Used memory: " + (usedMemory / (1024 * 1024)) + " MB");
        System.out.println("Max memory: " + (maxMemory / (1024 * 1024)) + " MB");
        System.out.println("----------");
    }
}
