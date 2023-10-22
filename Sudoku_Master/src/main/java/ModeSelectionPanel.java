import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelectionPanel extends JPanel {
    private StartScreen parent;

    public ModeSelectionPanel(StartScreen parent) {
        this.parent = parent;
        setLayout(new FlowLayout(FlowLayout.CENTER,100,40));

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
                int boardSize = promptForBoardSize();
                // Start the game with Easy Mode and the selected board size
                parent.startGame("Easy Mode", boardSize);
            }
        });

        JButton hardModeButton = new JButton("Hard Mode");
        hardModeButton.setBackground(Color.CYAN);
        hardModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int boardSize = promptForBoardSize();
                // Start the game with Hard Mode and the selected board size
                parent.startGame("Hard Mode", boardSize);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(zenModeButton);
        buttonPanel.add(easyModeButton);
        buttonPanel.add(hardModeButton);

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
}


