import javax.swing.*;
import java.awt.*;
public class SudokuSolver {
    private SudokuGrid SudokuGrid;

    public SudokuSolver(SudokuGrid grid) {
        SudokuGrid = grid;
    }

    public void solve() {
        JTextField[][] cells = SudokuGrid.getCells();
        int boardSize = cells.length;

        if (solveSudoku(cells, boardSize)) {
            // Sudoku is solvable, update the text fields with the solution
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    cells[row][col].setText(Integer.toString(Integer.parseInt(cells[row][col].getText())));
                }
            }
        } else {
            // Handle the case where the Sudoku is unsolvable
            JOptionPane.showMessageDialog(null, "Sudoku is unsolvable.");
        }
    }

    private boolean solveSudoku(JTextField[][] cells, int boardSize) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (cells[row][col].getText().isEmpty()) {
                    for (int num = 1; num <= boardSize; num++) {
                        if (isValidMove(cells, row, col, num)) {
                            cells[row][col].setText(Integer.toString(num));
                            if (solveSudoku(cells, boardSize)) {
                                return true;
                            }
                            cells[row][col].setText(""); // Backtrack
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidMove(JTextField[][] cells, int row, int col, int num) {
        String targetNum = Integer.toString(num);

        // Check the row
        for (int x = 0; x < cells.length; x++) {
            if (cells[row][x].getText().equals(targetNum)) {
                return false;
            }
        }

        // Check the column
        for (int y = 0; y < cells.length; y++) {
            if (cells[y][col].getText().equals(targetNum)) {
                return false;
            }
        }

        // Check the 3x3 subgrid
        int subgridSize = (int) Math.sqrt(cells.length);
        int subgridRowStart = row - row % subgridSize;
        int subgridColStart = col - col % subgridSize;
        for (int i = subgridRowStart; i < subgridRowStart + subgridSize; i++) {
            for (int j = subgridColStart; j < subgridColStart + subgridSize; j++) {
                if (cells[i][j].getText().equals(targetNum)) {
                    return false;
                }
            }
        }
        return true;
    }
}
