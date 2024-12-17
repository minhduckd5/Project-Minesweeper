package src;

import java.awt.*;
import java.awt.event.InputEvent;


public class BOT {
    public UI ui;

    public BOT(UI ui) {
        this.ui = UI.getInstance();
    }

    public void startPlaying() {
        try {
            Robot robot = new Robot();
            int[] field = ui.getField();
            int rows = ui.getN_ROWS();
            int cols = ui.getN_COLS();
            int cellSize = ui.getCellSize();
            
            
            // Top-left corner of the board
            int boardTopLeftX = (ui.getWidth() - (cols * cellSize)) / 2;
            int boardTopLeftY = (ui.getHeight() - (rows * cellSize)) / 2;

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    int index = row * cols + col;
                    if (field[index] == ui.getCoveredMineCell()) {
                        // Example: Reveal the cell
                        int x = boardTopLeftX + col * cellSize;
                        int y = boardTopLeftY + row * cellSize;

                        robot.mouseMove(x, y);
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                        // Add delays to simulate human interaction
                        Thread.sleep(100);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
