package src.main.java;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private UserRegistration userRegistrationPanel;
    private UserLogin userLoginPanel;
    private JPanel mainMenuPanel; // Placeholder for main menu

    public StartScreen() {
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 200));

        // Set up a card layout to switch between panels
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Create the registration panel
        userRegistrationPanel = new UserRegistration(this);

        // Create the login panel
        userLoginPanel = new UserLogin(this);

        ModeSelectionPanel modeSelectionPanel = new ModeSelectionPanel(this);

        // Create the main menu panel (placeholder)
        mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new BorderLayout());
        mainMenuPanel.add(new JLabel("Welcome to the Main Menu!"), BorderLayout.CENTER);

        // Create the start screen panel
        JPanel startPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the registration panel
                switchToRegistrationPanel();
            }
        });

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the login panel
                switchToLoginPanel();
            }
        });

        constraints.insets = new Insets(20, 20, 20, 20);
        startPanel.add(registerButton, constraints);
        startPanel.add(loginButton, constraints);

        cardPanel.add(startPanel, "start");
        cardPanel.add(userRegistrationPanel, "userRegistration");
        cardPanel.add(userLoginPanel, "userLogin");
        cardPanel.add(mainMenuPanel, "mainMenu");
        cardPanel.add(modeSelectionPanel, "modeSelection");

        cardLayout.show(cardPanel, "start");

        add(cardPanel);
        pack();
        setLocationRelativeTo(null);
    }

    public void switchToRegistrationPanel() {
        cardLayout.show(cardPanel, "userRegistration");
    }

    public void switchToLoginPanel() {
        cardLayout.show(cardPanel, "userLogin");
    }

    //    public void switchToMainMenuPanel(String username) {
//        // Modify this method as needed based on your actual main menu requirements
//        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
////        mainMenuPanel.removeAll();
////        mainMenuPanel.add(welcomeLabel, BorderLayout.CENTER);
////        cardLayout.show(cardPanel, "mainMenu");
//    }
    public void switchToMainMenuPanel(String username) {
        // Modify this method as needed based on your actual main menu requirements
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the mode selection panel
                cardLayout.show(cardPanel, "modeSelection");
            }
        });

        mainMenuPanel.removeAll();
        mainMenuPanel.setLayout(new BorderLayout());
        mainMenuPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainMenuPanel.add(startButton, BorderLayout.CENTER);
        cardLayout.show(cardPanel, "mainMenu");
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

    public CardLayout getCardLayout()
    {
        return cardLayout;
    }

    public JPanel getCardPanel()
    {
        return cardPanel;
    }
}
