package test.java.examples;

public class MatrixAnagram {
    int matrix[][] = new int[][]{
            {1, 2, 3, 2, 4},
            {1, 4, 3, 3, 1},
            {1, 5, 3, 8, 4},
            {1, 3, 9, 2, 3},
            {1, 2, 3, 3, 4}
    };

    public void printAnagram() {
        printRow(matrix,0,false);
    }

    public void internalPrint(int start, int end) {

    }

    public void printRow(int[][] m, int rowId, boolean reverse) {
        if (!reverse) {
            for (int i = rowId; i < m[rowId].length; i++) {
                System.out.println(matrix[rowId][i]);
            }
        } else {
            for (int i = m[rowId].length - 1; i >= 0; i--) {
                System.out.println(matrix[rowId][i]);
            }
        }
    }

    public void printColumn(int[][] m, int colId, boolean reverse) {
        if (!reverse) {
            for (int i = 0; i < m.length; i++) {
                System.out.println(matrix[i][colId]);
            }
        } else {
            for (int i = m.length - 1; i >= 0; i--) {
                System.out.println(matrix[i][colId]);
            }
        }
    }

    public static void main(String[] args) {
        MatrixAnagram ma = new MatrixAnagram();
        ma.printRow(ma.matrix, 0, true);
//        ma.printColumn(ma.matrix, 4, true);

    }
}
