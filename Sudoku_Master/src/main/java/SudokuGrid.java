package Sudoku_Master.src.main.java;

import java.awt.*;
import javax.swing.*;
public class SudokuGrid
{
    private JTextField[][] cells;
    private JPanel gridPanel;
    private int boardSize;

    public SudokuGrid(int size) {
        boardSize = size;
        cells = new JTextField[boardSize][boardSize];
        gridPanel = new JPanel(new GridLayout(boardSize, boardSize));

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setFont(new Font("Arial", Font.PLAIN, 24));
                cells[row][col].setBackground(Color.CYAN);
                gridPanel.add(cells[row][col]);
            }
        }
    }

    public JTextField[][] getCells() {
        return cells;
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }

    public JTextField getCell(int row,int col){return cells[row][col];}
}