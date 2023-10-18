import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class SudokuBoard extends JFrame{
    private int boardSize;
    private SudokuGrid sudokuGrid;
    public SudokuBoard(int size)
    {
        boardSize = size;
        sudokuGrid = new SudokuGrid(size);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        setTitle("Sudoku Master");

//        SudokuGrid sudokuGrid = new SudokuGrid(size);
        SudokuSolver sudokuSolver = new SudokuSolver(sudokuGrid);

        JPanel buttonPanel = new JPanel();
        JButton solveButton = new JButton("Solution");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudokuSolver.solve();
            }
        });
        buttonPanel.add(solveButton);

        JButton checkSolutionButton = new JButton("Check");
        checkSolutionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean solutionCorrect = isSolutionCorrect(boardSize);
                if(solutionCorrect)
                {
                    JOptionPane.showMessageDialog(null,"Solution is Correct!");
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Solution is Incorrect.Please check again");
                }
            }
        });
        buttonPanel.add(checkSolutionButton);

        JButton restartButton = new JButton("Restart Game");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField[][] cells = sudokuGrid.getCells();;
                for(int row=0;row<size;row++)
                {
                    for(int col=0;col<size;col++)
                    {
                        cells[row][col].setText("");
                    }
                }
            }
        });
        buttonPanel.add(restartButton);

        Container container = getContentPane();
        sudokuGrid.getGridPanel().setBackground(Color.BLUE);
        container.setLayout(new BorderLayout());
        container.add(sudokuGrid.getGridPanel(), BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private boolean isSolutionCorrect(int boardSize)
    {
        JTextField[][] cells = sudokuGrid.getCells();
        for (int row = 0; row < boardSize; row++) {
            Set<String> rowSet = new HashSet<>();
            Set<String> colSet = new HashSet<>();
            Set<String> subgridSet = new HashSet<>();
            int subgridsize = (int) Math.sqrt(boardSize);
            for (int col = 0; col < boardSize; col++) {
                String rowCellValue = sudokuGrid.getCell(row, col).getText().trim();
                String colCellValue = sudokuGrid.getCell(col, row).getText().trim();
                String subgridCellValue = sudokuGrid.getCell((row / subgridsize) * subgridsize + (col / subgridsize), (row % subgridsize) * subgridsize + (col % subgridsize)).getText().trim();

                if (rowCellValue.isEmpty() || rowSet.contains(rowCellValue)
                        || colCellValue.isEmpty() || colSet.contains(colCellValue)
                        || subgridCellValue.isEmpty() || subgridSet.contains(subgridCellValue)
                        || !isValidCellValue(rowCellValue) || !isValidCellValue(colCellValue) || !isValidCellValue(subgridCellValue)) {
                    return false;
                }
                rowSet.add(rowCellValue);
                colSet.add(colCellValue);
                subgridSet.add(subgridCellValue);
            }
        }
        return true;
    }

    private boolean isValidCellValue(String value)
    {
        try
        {
            int num = Integer.parseInt(value);
            return num>=1 && num <= boardSize;
        } catch(NumberFormatException e)
        {
            return false;
        }
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
