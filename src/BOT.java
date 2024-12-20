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


                // Skip already uncovered or flagged cells
                if (field[index] <= ui.getMineCell()) {
                    continue;
                }


                    if (field[index] == ui.getCoveredMineCell()) {
                        // Example: Reveal the cell
                        int x = boardTopLeftX + col * cellSize;
                        int y = boardTopLeftY + row * cellSize;

                        robot.mouseMove(x, y);
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                        // Add delays to simulate human interaction
                        Thread.sleep(1000);

                          // Check for empty cells to uncover adjacent cells
                    if (field[index] == ui.getEmptyCell()) {
                        uncoverAdjacentCells(row, col, rows, cols, field, robot, boardTopLeftX, boardTopLeftY, cellSize);
                    }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void uncoverAdjacentCells(int row, int col, int rows, int cols, int[] field, Robot robot, int boardTopLeftX, int boardTopLeftY, int cellSize) {
    int[] directions = {-1, 0, 1}; // Check all directions: -1 (left/up), 0 (same), 1 (right/down)

    for (int dx : directions) {
        for (int dy : directions) {
            if (dx == 0 && dy == 0) continue; // Skip the current cell

            int newRow = row + dx;
            int newCol = col + dy;

            // Ensure the new cell is within bounds
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                int newIndex = newRow * cols + newCol;

                // Only uncover covered cells
                if (field[newIndex] > ui.getMineCell()) {
                    int x = boardTopLeftX + newCol * cellSize;
                    int y = boardTopLeftY + newRow * cellSize;

                    try {
                        robot.mouseMove(x, y);
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // If the new cell is empty, continue uncovering
                    if (field[newIndex] == ui.getEmptyCell()) {
                        uncoverAdjacentCells(newRow, newCol, rows, cols, field, robot, boardTopLeftX, boardTopLeftY, cellSize);
                    }
                }
            }
        }
    }
}

}
