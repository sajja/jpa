package test.java.examples;

import java.util.Random;

public class FllodFill {
    int matrix[][] = new int[][]{
            {1, 2, 3, 2, 4},
            {1, 4, 3, 3, 1},
            {1, 5, 3, 8, 4},
            {1, 3, 9, 2, 3},
            {1, 2, 3, 3, 4}
    };

    public void fillMatrix() {
        Random r = new Random();
        for (int y = 0; y < matrix[y].length - 1; y++) {
            for (int x = 0; x < matrix[x].length - 1; x++) {
                matrix[y][x] = r.nextInt(9);
            }
        }
    }

    public void printMatrix() {
        for (int y = 0; y < (matrix.length); y++) {
            for (int x = 0; x < (matrix[y].length); x++) {
                System.out.print("|" + matrix[y][x] + "|");
            }
            System.out.println("");
        }
    }

    public void fill(int x, int y, int newVal) {
        if (y >= 0 && y < matrix.length && x >= 0 && x < matrix[y].length) {
            int oldVal = matrix[y][x];
            fillInternal(x, y, oldVal, newVal, true);
        }
    }

    public void fillInternal(int x, int y, int oldVal, int newVal, boolean force) {
        if (y >= 0 && y < matrix.length && x >= 0 && x < matrix[y].length && (matrix[y][x] == oldVal || force)) {
            matrix[y][x] = newVal;
            fillInternal(x - 1, y - 1, oldVal, newVal, false);
            fillInternal(x, y - 1, oldVal, newVal, false);
            fillInternal(x + 1, y - 1, oldVal, newVal, false);
            fillInternal(x - 1, y, oldVal, newVal, false);
            fillInternal(x + 1, y, oldVal, newVal, false);
            fillInternal(x - 1, y + 1, oldVal, newVal, false);
            fillInternal(x, y + 1, oldVal, newVal, false);
            fillInternal(x + 1, y + 1, oldVal, newVal, false);
        }
    }


    public static void main(String[] args) {
        FllodFill t = new FllodFill();
        t.printMatrix();
//        System.out.println(t.matrix[2][2]);
        t.fill(2, 2, 2);
        System.out.println("-------------------------------------");
        t.printMatrix();
    }
}
