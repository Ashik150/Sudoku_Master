import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class SudokuGenerator {
    private int[][] puzzle;
    private int boardSize;
    private Random random;

    public SudokuGenerator(int size) {
        boardSize = size;
        puzzle = new int[boardSize][boardSize];
        random = new Random();
    }

    public int[][] generatePuzzle() {
        generateSolution(0, 0);
        return puzzle;
    }

    private boolean generateSolution(int row, int col) {
        if (row == boardSize - 1 && col == boardSize) {
            return true;
        }

        if (col == boardSize) {
            col = 0;
            row++;
        }

        List<Integer> numbers = new ArrayList<>();
        for(int i=1;i<=boardSize;i++)
        {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        for (int num : numbers) {
            if (isValidMove(row, col, num)) {
                puzzle[row][col] = num;
                if (generateSolution(row, col + 1)) {
                    return true;
                }
                puzzle[row][col] = 0;
            }
        }
        return false;
    }

    private boolean isValidMove(int row, int col, int num) {
        int subgridSize = (int) Math.sqrt(boardSize);
        return isValidInRow(row, num) && isValidInColumn(col, num) && isValidInBox(row - row % subgridSize, col - col % subgridSize, num);
    }

    private boolean isValidInRow(int row, int num) {
        for (int col = 0; col < boardSize; col++) {
            if (puzzle[row][col] == num) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidInColumn(int col, int num) {
        for (int row = 0; row < boardSize; row++) {
            if (puzzle[row][col] == num) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidInBox(int row, int col, int num) {
        int subgridSize = (int) Math.sqrt(boardSize);
        for (int r = 0; r < subgridSize; r++) {
            for (int c = 0; c < subgridSize; c++) {
                if (puzzle[row + r][col + c] == num) {
                    return false;
                }
            }
        }
        return true;
    }
}


