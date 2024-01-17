package src.main.java;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuTimer {
    private Timer timer;
    private int timeLimitInSeconds;
    private int currentTimeInSeconds;
    private SudokuBoard sudokuBoard;

    public SudokuTimer(int timeLimitInSeconds, SudokuBoard sudokuBoard) {
        this.timeLimitInSeconds = timeLimitInSeconds;
        this.sudokuBoard = sudokuBoard;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentTimeInSeconds--;
                sudokuBoard.updateTimerDisplay();

                if (currentTimeInSeconds <= 0) {
                    stopTimer();
                    sudokuBoard.handleTimeUp();
                }
            }
        });
    }

    public void startTimer() {
        currentTimeInSeconds = timeLimitInSeconds;
        sudokuBoard.updateTimerDisplay();
        timer.start();
    }

    public void stopTimer() {

        timer.stop();
    }
}