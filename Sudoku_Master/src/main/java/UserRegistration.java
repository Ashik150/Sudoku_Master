package src.main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class UserRegistration extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private StartScreen startScreen;

    public UserRegistration(StartScreen startScreen) {
        this.startScreen = startScreen;

        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel()); // Placeholder
        add(registerButton);
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (!username.isEmpty() && !password.isEmpty()) {
            if (createUser(username, password)) {
                JOptionPane.showMessageDialog(this, "User registration successful!");

                // Switch to the login panel after successful registration
                startScreen.switchToLoginPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register user. Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
        }
    }

    private boolean createUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_accounts.txt", true))) {
            // Append user credentials to a text file (for simplicity; in practice, consider secure storage)
            writer.write(username + ";" + password + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
