package assignment10.multithreading;
class MatrixMultiplication {
    private final int[][] result;
    private final int[][] matrix1;
    private final int[][] matrix2;
    private final int rows1, cols1, cols2;
    

    public MatrixMultiplication(int[][] matrix1, int[][] matrix2) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.rows1 = matrix1.length;
        this.cols1 = matrix1[0].length;
        this.cols2 = matrix2[0].length;
        this.result = new int[rows1][cols2];
    }

    public void multiply() throws InterruptedException {
        Thread[] threads = new Thread[rows1];  

        for (int i = 0; i < rows1; i++) {
            final int row = i;  
            threads[i] = new Thread(() -> multiplyRow(row));
            threads[i].start(); 
        }

        for (Thread thread : threads) {
            thread.join();  
        }
    }

    private synchronized void multiplyRow(int row) {
        for (int j = 0; j < cols2; j++) {
            for (int k = 0; k < cols1; k++) {
                result[row][j] += matrix1[row][k] * matrix2[k][j];
            }
        }
    }

    public void printResult() {
        for (int[] row : result) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

   
}

public class Program1 {

	public static void main(String[] args) throws InterruptedException {
		int[][] matrix1 = {
	            {1, 2, 3},
	            {4, 5, 6},
	            {7, 8, 9}
	        };
	        int[][] matrix2 = {
	            {1, 0, 1},
	            {0, 1, 0},
	            {1, 0, 1}
	        };

	        MatrixMultiplication mm = new MatrixMultiplication(matrix1, matrix2);
	        mm.multiply();  
	        System.out.println("Result of Matrix Multiplication:");
	        mm.printResult();  

	}

}
