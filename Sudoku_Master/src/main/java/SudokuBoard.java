import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class SudokuBoard extends JFrame {
    private int boardSize;

    private String mode;
    private int hintCounter;
    private SudokuGrid sudokuGrid;
    private int[][] solvedpuzzle;
    private Timer timer;
    private long startTime;
    private JLabel timerLabel;
    private int timeLimitInSeconds;
    private JLabel hintCounterLabel;// Time limit for the game

    public SudokuBoard(String mode, int size) {
        boardSize = size;
        this.mode = mode;
        sudokuGrid = new SudokuGrid(size);
        solvedpuzzle = new int[boardSize][boardSize];

        // Initialize the time limit based on the game mode
        if ("Easy Mode".equals(mode)) {
            timeLimitInSeconds = 8 * 60; // 8 minutes for easy mode
        } else if ("Hard Mode".equals(mode)) {
            timeLimitInSeconds = 5 * 60; // 5 minutes for hard mode
        } else {
            timeLimitInSeconds = 0; // No time limit for other modes (e.g., Zen Mode)
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(550, 500));
        setTitle("Sudoku Master");

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimerDisplay();
            }
        });

        Container container1 = getContentPane();
        container1.setLayout(new BorderLayout());

        timerLabel = new JLabel("Time: 00:00");
        container1.add(timerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton solveButton = new JButton("Solution");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SudokuSolver sudokuSolver = new SudokuSolver(sudokuGrid);
                sudokuSolver.solve();
            }
        });
        buttonPanel.add(solveButton);

        JButton checkSolutionButton = new JButton("Check");
        checkSolutionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                boolean solutionCorrect = isSolutionCorrect(boardSize);
                if (solutionCorrect) {
                    JOptionPane.showMessageDialog(null, "Congratulations! Solution is correct.");
                } else {
                    JOptionPane.showMessageDialog(null, "Solution is incorrect. Please check your entries.");
                }
            }
        });
        buttonPanel.add(checkSolutionButton);

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBackToStartScreen();
            }
        });
        buttonPanel.add(back);

        JButton generatePuzzleButton = new JButton("Restart Game");
        generatePuzzleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                startTime = System.currentTimeMillis();
                timer.start();
                generateSudokuPuzzle();
            }
        });
        buttonPanel.add(generatePuzzleButton);
        hintCounterLabel = new JLabel("Hints remaining: " + hintCounter);
        JButton hintButton = new JButton("Hint");
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                provideHint();
            }
        });
        buttonPanel.add(hintButton);
        buttonPanel.add(hintCounterLabel);

        if ("Zen Mode".equals(mode)) {
            hintCounter = 3;
        } else if ("Easy Mode".equals(mode)) {
            hintCounter = 2;
        } else if ("Hard Mode".equals(mode)) {
            hintCounter = 1;
        }



        container1.add(sudokuGrid.getGridPanel(), BorderLayout.CENTER);
        container1.add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        generateSudokuPuzzle();


        startTime = System.currentTimeMillis();
        timer.start();
    }

    public void updateTimerDisplay() {
        if (timeLimitInSeconds > 0) {
            long currentTime = System.currentTimeMillis();
            long elapsedTimeInSeconds = (currentTime - startTime) / 1000;
            long remainingTimeInSeconds = timeLimitInSeconds - elapsedTimeInSeconds;

            if (remainingTimeInSeconds <= 0) {
                handleTimeUp();
            }
            else
            {
                long hours = remainingTimeInSeconds / 3600;
                long minutes = (remainingTimeInSeconds % 3600) / 60;
                long seconds = remainingTimeInSeconds % 60;
                String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                timerLabel.setText("Time: " + formattedTime);
            }
        }
    }

    public void handleTimeUp() {
        // Stop the timer (it might already be stopped, but this ensures it's stopped)
        timer.stop();

        // Display a message indicating that the player has run out of time
        JOptionPane.showMessageDialog(null, "Time's up! You ran out of time. Better luck next time!");

        // Reset the board and start a new game
        resetBoard();
        generateSudokuPuzzle();
        startTime = System.currentTimeMillis();
        timer.start(); // Restart the timer for the new game
    }

    private void goBackToStartScreen() {
        this.setVisible(false);
        StartScreen startScreen = new StartScreen();
        startScreen.setVisible(true);
        dispose();
    }
    private void resetHintCounter() {
        // Reset the hint counter based on the game mode
        if ("Zen Mode".equals(mode)) {
            hintCounter = 3;
        } else if ("Easy Mode".equals(mode)) {
            hintCounter = 2;
        } else if ("Hard Mode".equals(mode)) {
            hintCounter = 1;
        }
        updateHintCounterLabel();
    }
    private void resetBoard() {
        JTextField[][] cells = sudokuGrid.getCells();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                cells[row][col].setText("");
            }
        }
    }

    private void generateSudokuPuzzle() {
        SudokuGenerator generator = new SudokuGenerator(boardSize);
        solvedpuzzle = generator.generatePuzzle();
        resetBoard(); // Clear the board
        resetHintCounter();
        populateBoard(solvedpuzzle, boardSize); // Populate the board with the solution
        removeNumbersFromBoard();
    }

    private void populateBoard(int[][] puzzle, int boardSize) {
        JTextField[][] cells = sudokuGrid.getCells();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int value = puzzle[row][col];
                if (value != 0) {
                    cells[row][col].setText(Integer.toString(value));
                    cells[row][col].setEditable(false);
                }
            }
        }
    }

    private void removeNumbersFromBoard() {
        JTextField[][] cells = sudokuGrid.getCells();
        int cellsToRemove = boardSize * boardSize / 2; // Adjust as needed

        while (cellsToRemove > 0) {
            int row = (int) (Math.random() * boardSize);
            int col = (int) (Math.random() * boardSize);

            if (!cells[row][col].getText().isEmpty()) {
                cells[row][col].setText("");
                cells[row][col].setEditable(true);
                cellsToRemove--;
            }
        }
    }

    private boolean isSolutionCorrect(int boardSize) {
        JTextField[][] cells = sudokuGrid.getCells();

        // Check rows, columns, and subgrids for correctness
        // Implement the solution correctness checking logic here using the getCell method
        for (int row = 0; row < boardSize; row++) {
            Set<String> rowSet = new HashSet<>();
            Set<String> colSet = new HashSet<>();
            Set<String> subgridSet = new HashSet<>();
            int subgridSize = (int) Math.sqrt(boardSize);
            for (int col = 0; col < boardSize; col++) {
                String rowCellValue = cells[row][col].getText().trim();
                String colCellValue = cells[col][row].getText().trim();
                String subgridCellValue = cells[(row / subgridSize) * subgridSize + (col / subgridSize)][(row % subgridSize) * subgridSize + (col % subgridSize)].getText().trim();

                if (rowCellValue.isEmpty() || rowSet.contains(rowCellValue) ||
                        colCellValue.isEmpty() || colSet.contains(colCellValue) ||
                        subgridCellValue.isEmpty() || subgridSet.contains(subgridCellValue) ||
                        !isValidCellValue(rowCellValue) || !isValidCellValue(colCellValue) || !isValidCellValue(subgridCellValue)) {
                    return false; // Invalid row, column, or subgrid
                }
                rowSet.add(rowCellValue);
                colSet.add(colCellValue);
                subgridSet.add(subgridCellValue);
            }
        }
        return true; // Solution is correct
    }

    private boolean isValidCellValue(String value) {
        try {
            int num = Integer.parseInt(value);
            return num >= 1 && num <= boardSize;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private void provideHint() {
        if (hintCounter > 0) {
            // Randomly select an empty cell and fill it with the correct value from the solution
            JTextField[][] cells = sudokuGrid.getCells();
            int emptyCells = getEmptyCellsCount(cells);

            if (emptyCells > 0) {
                int randomEmptyIndex = (int) (Math.random() * emptyCells);
                int count = 0;

                for (int row = 0; row < boardSize; row++) {
                    for (int col = 0; col < boardSize; col++) {
                        if (cells[row][col].getText().isEmpty()) {
                            if (count == randomEmptyIndex) {
                                // Set the hint value from the solution
                                cells[row][col].setText(Integer.toString(solvedpuzzle[row][col]));
                                cells[row][col].setEditable(false);
                                hintCounter--;

                                // Update the hint counter display if needed
                                // (you can add a JLabel or another GUI element to display the remaining hints)
                                // hintCounterLabel.setText("Hints remaining: " + hintCounter);
                                updateHintCounterLabel();
                                return;
                            }
                            count++;
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No more hints available.");
        }
    }
    private void updateHintCounterLabel() {
        hintCounterLabel.setText("Hints remaining: " + hintCounter);
    }
    private int getEmptyCellsCount(JTextField[][] cells) {
        int count = 0;
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (cells[row][col].getText().isEmpty()) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StartScreen startScreen = new StartScreen();
                startScreen.setVisible(true);
            }
        });
    }
}
