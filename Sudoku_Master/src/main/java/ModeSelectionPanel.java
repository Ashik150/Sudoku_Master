package src.main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelectionPanel extends JPanel {
    private StartScreen parent;

    public ModeSelectionPanel(StartScreen parent) {
        this.parent = parent;
        setLayout(new FlowLayout(FlowLayout.CENTER,100,25));

        JButton zenModeButton = new JButton("Zen Mode");
        zenModeButton.setBackground(Color.CYAN);
        zenModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int boardSize = promptForBoardSize();
                // Start the game with Zen Mode and the selected board size
                parent.startGame("Zen Mode", boardSize);
            }
        });

        JButton easyModeButton = new JButton("Easy Mode");
        easyModeButton.setBackground(Color.CYAN);
        easyModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int boardSize = 9;
                // Start the game with Easy Mode and the selected board size
                parent.startGame("Easy Mode", boardSize);
            }
        });

        JButton hardModeButton = new JButton("Hard Mode");
        hardModeButton.setBackground(Color.CYAN);
        hardModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int boardSize = 16;
                // Start the game with Hard Mode and the selected board size
                parent.startGame("Hard Mode", boardSize);
            }
        });
        JButton personalChallengeButton = new JButton("Personal Challenge");
        personalChallengeButton.setBackground(Color.CYAN);
        personalChallengeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int boardSize = promptForBoardSize();
                int timeLimit = promptForTimeLimit(); // Prompt user for time limit
                parent.startGame("Personal Challenge", boardSize, timeLimit);
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.CYAN);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(zenModeButton);
        buttonPanel.add(easyModeButton);
        buttonPanel.add(hardModeButton);
        buttonPanel.add(personalChallengeButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private int promptForBoardSize() {
        String sizeInput = JOptionPane.showInputDialog("Enter the Board Size:");
        int boardSize = 4; // Default size
        try {
            int size = Integer.parseInt(sizeInput);
            double sqrt = Math.sqrt(size);
            if (sqrt == (int) sqrt) {
                boardSize = size;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid Input. Using Default Size (4X4).");
        }
        return boardSize;
    }
    private int promptForTimeLimit() {
        String timeLimitInput = JOptionPane.showInputDialog("Enter the Time Limit (in seconds) for Personal Challenge:");
        int timeLimit = 4; // Default time limit
        try {
            timeLimit = Integer.parseInt(timeLimitInput);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid Input. Using Default Time Limit (4 minutes).");
        }
        return timeLimit;
    }

    public void logout() {
        // Retrieve the parent StartScreen
        StartScreen startScreen = parent;

        // Switch to the start panel
        startScreen.getCardLayout().show(startScreen.getCardPanel(), "start");
    }
}


