import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JFrame {
    public StartScreen() {
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 200));

        // Set the background color of the content pane
        getContentPane().setBackground(Color.BLUE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JButton newGameButton = new JButton("Start");
        newGameButton.setBackground(Color.GREEN);

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sizeInput = JOptionPane.showInputDialog("Enter the Board Size:");
                int boardsize=4;
                try{
                    int size = Integer.parseInt(sizeInput);
                    double sqrt = Math.sqrt(size);
                    if(sqrt == (int)sqrt)
                    {
                        boardsize = size;
                    }
                } catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null,"Invalid Input. Using Default Size (9X9).");
                }
                SudokuBoard sudokuBoard = new SudokuBoard(boardsize);
                sudokuBoard.setVisible(true);
                dispose();
            }
        });

        constraints.insets = new Insets(20, 20, 20, 20);
        panel.add(newGameButton, constraints);

        add(panel);
        pack();
        setLocationRelativeTo(null);
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
