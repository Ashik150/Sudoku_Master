import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class SudokuBoard extends JFrame{
    public SudokuBoard(int size)
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        setTitle("Sudoku Master");

        SudokuGrid sudokuGrid = new SudokuGrid(size);
        SudokuSolver sudokuSolver = new SudokuSolver(sudokuGrid);

        JPanel buttonPanel = new JPanel();
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudokuSolver.solve();
            }
        });
        buttonPanel.add(solveButton);

        Container container = getContentPane();
        sudokuGrid.getGridPanel().setBackground(Color.BLUE);
        container.setLayout(new BorderLayout());
        container.add(sudokuGrid.getGridPanel(), BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int boardSize = 9; // Change this to the desired board size
                SudokuBoard sudokuBoard = new SudokuBoard(boardSize);
                sudokuBoard.setVisible(true);
            }
        });
    }
}
