package Sudoku_Master.src.main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserLogin extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private StartScreen startScreen;

    public UserLogin(StartScreen startScreen) {
        this.startScreen = startScreen;

        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel()); // Placeholder
        add(loginButton);
    }

    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (!username.isEmpty() && !password.isEmpty()) {
            if (checkUserCredentials(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");

                // Added comment: Switch to main menu panel in StartScreen
                startScreen.switchToMainMenuPanel(username);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
        }
    }

    private boolean checkUserCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("user_accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true; // Found matching credentials
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // No matching credentials found
    }
}
