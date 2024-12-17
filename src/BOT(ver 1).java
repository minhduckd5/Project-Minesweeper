// package src;
// import java.awt.event.MouseEvent;
// import java.util.*;

// public class BOT {
//     private final UI ui; // Reference to the UI class
//     private final Random random;
//     private boolean firstMove = true;

//     public BOT(UI ui) {
//         this.ui = ui;
//         this.random = new Random();
//     }

//     // Start the game and let the bot play
//     public void startPlaying() {
//         while (ui.isInGame()) {
//             playTurn();
//             try {
//                 Thread.sleep(500); // Delay for a better simulation experience
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//         }
//     }

//     // The bot will play its turn
//     private void playTurn() {
//         if (firstMove) {
//             makeFirstMove();
//             firstMove = false;
//         } else {
//             makeMove();
//         }
//     }

//     // The bot makes its first move
//     private void makeFirstMove() {
//         int emptyCell = getRandomUncoveredCell(); // Find an uncovered cell to start the game
//         uncoverCell(emptyCell);
//     }

//     // The bot makes a random move after the first
//     private void makeMove() {
//         // Step 1: Look for uncovered cells and choose one to click
//         int uncoveredCell = getNextUncoveredCell();

//         if (uncoveredCell == -1) {
//             // No more moves left, the game is over
//             return;
//         }

//         uncoverCell(uncoveredCell);

//         // Step 2: Check surrounding cells for mines and mark them
//         markMines(uncoveredCell);
//     }

//     // Get the next uncovered cell to click, based on the current game state
//     private int getNextUncoveredCell() {
//         // Try to find a cell that is uncovered and not already clicked
//         for (int i = 0; i < ui.getField().length; i++) {
//             if (ui.getField()[i] > ui.getCoveredMineCell() && ui.getField()[i] < ui.getMarkedMineCell()) {
//                 return i; // Return the first uncovered cell
//             }
//         }
//         return -1; // No uncovered cell found
//     }

//     // Get a random uncovered cell for the first move
//     private int getRandomUncoveredCell() {
//         List<Integer> uncoveredCells = new ArrayList<>();
//         for (int i = 0; i < ui.getField().length; i++) {
//             if (ui.getField()[i] > ui.getCoveredMineCell()) {
//                 uncoveredCells.add(i); // Add all uncovered cells to the list
//             }
//         }
//         if (uncoveredCells.isEmpty()) {
//             return -1; // No uncovered cells available
//         }
//         return uncoveredCells.get(random.nextInt(uncoveredCells.size())); // Pick a random one
//     }

//     // Uncover the given cell and handle any adjacent empty cells
//     private void uncoverCell(int cellIndex) {
//         // Get the row and column based on cellIndex
//         int row = cellIndex / UI.N_COLS;
//         int col = cellIndex % UI.N_COLS;

//         // If the cell is already uncovered, do nothing
//         if (ui.getField()[cellIndex] <= ui.getCoveredMineCell()) {
//             return;
//         }

//         // Simulate a left-click to uncover the cell
//         MouseEvent simulatedClick = new MouseEvent(ui, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(),
//                 0, col * ui.getCellSize(), row * ui.getCellSize(), 1, false, MouseEvent.BUTTON1);

//         ui.getMouseListeners()[0].mousePressed(simulatedClick);
//     }

//     // Mark mines based on surrounding cells
//     private void markMines(int uncoveredCell) {
//         int currentCol = uncoveredCell % UI.N_COLS;
//         int[] directions = {-UI.N_COLS - 1, -UI.N_COLS, -UI.N_COLS + 1, -1, 1, UI.N_COLS - 1, UI.N_COLS, UI.N_COLS + 1};

//         // Check the neighboring cells
//         for (int direction : directions) {
//             int neighbor = uncoveredCell + direction;

//             // Skip out-of-bound cells
//             if (neighbor < 0 || neighbor >= ui.getField().length) {
//                 continue;
//             }

//             // If the neighbor is not uncovered, continue
//             if (ui.getField()[neighbor] <= ui.getCoveredMineCell()) {
//                 continue;
//             }

//             // If the neighbor is a possible mine, mark it
//             if (ui.getField()[neighbor] > ui.getMineCell()) {
//                 markMine(neighbor);
//             }
//         }
//     }

//     // Mark the given cell as a mine (right-click)
//     private void markMine(int cellIndex) {
//         // Get the row and column based on cellIndex
//         int row = cellIndex / UI.N_COLS;
//         int col = cellIndex % UI.N_COLS;

//         // Simulate a right-click to mark the mine
//         MouseEvent simulatedClick = new MouseEvent(ui, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(),
//                 0, col * ui.getCellSize(), row * ui.getCellSize(), 1, false, MouseEvent.BUTTON3);

//         ui.getMouseListeners()[0].mousePressed(simulatedClick);
//     }
// }
