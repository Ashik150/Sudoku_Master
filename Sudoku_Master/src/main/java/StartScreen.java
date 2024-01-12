package Sudoku_Master.src.main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private UserRegistration userRegistrationPanel; // Declare the UserRegistration panel

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
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the user registration panel
                cardLayout.show(cardPanel, "userRegistration");
            }
        });

        constraints.insets = new Insets(20, 20, 20, 20);
        startPanel.add(startButton, constraints);
        startPanel.add(registerButton, constraints);
        // Create the mode selection panel
        ModeSelectionPanel modeSelectionPanel = new ModeSelectionPanel(this);
        // Create the user registration panel and pass the reference to StartScreen
        userRegistrationPanel = new UserRegistration(this);



        cardPanel.add(startPanel, "start");
        cardPanel.add(modeSelectionPanel, "modeSelection");
        cardPanel.add(userRegistrationPanel, "userRegistration");
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

    // Method to switch to the registration panel after successful registration
    public void switchToRegistrationPanel() {
        cardLayout.show(cardPanel, "userRegistration");
        //userRegistrationPanel.clearFields(); // Optional: clear fields in the registration panel
    }
}
