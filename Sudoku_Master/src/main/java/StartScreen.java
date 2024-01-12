import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public StartScreen() {
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 200));

        // Set up a card layout to switch between panels
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Create the start screen panel
        JPanel startPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JButton startButton = new JButton("Start");
        startButton.setBackground(Color.GREEN);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the mode selection panel
                cardLayout.show(cardPanel, "modeSelection");
            }
        });

        constraints.insets = new Insets(20, 20, 20, 20);
        startPanel.add(startButton, constraints);

        // Create the mode selection panel
        ModeSelectionPanel modeSelectionPanel = new ModeSelectionPanel(this);

        cardPanel.add(startPanel, "start");
        cardPanel.add(modeSelectionPanel, "modeSelection");
        cardLayout.show(cardPanel, "start");

        add(cardPanel);
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

    public void startGame(String mode, int boardSize, int... extraParameters) {
        // Start the game with the selected mode and board size
        SudokuBoard sudokuBoard = new SudokuBoard(mode, boardSize, extraParameters);
        sudokuBoard.setVisible(true);
        dispose();
    }


}
