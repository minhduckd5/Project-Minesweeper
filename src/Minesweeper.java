package src;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Minesweeper extends JFrame implements GameEndListener {
    private UI ui;
    private final JLabel status;
    private GameStatsManager statsManager;
    private String currentDifficulty;

    // Constructor to initialize the Minesweeper game
    public Minesweeper() {
        // Initialize the status label
        status = new JLabel("");

        // Initialize input panel to get user inputs
        InputPanel.getInput();

        // Create the menu bar and pass the current instance of Minesweeper to it
        MenuBar menuBar = new MenuBar(this);
        // Add the menu bar to the frame
        setJMenuBar(menuBar);

        // UI with the current instance of Minesweeper and status label
        ui = new UI(this, status);
        add(status, BorderLayout.SOUTH); // Add status label at the bottom of the frame
        add(ui); // Add the UI component to the frame

        // Allow the frame to be resizable and adjust its size to fit the components
        setResizable(true);
        pack();

        // Set the title of the frame
        setTitle("Minesweeper");

        // Initialize the game statistics manager
        statsManager = new GameStatsManager();
        // Get the current difficulty level from the input panel
        currentDifficulty = InputPanel.getSelectedDifficulty();

        // Center the frame on the screen
        setLocationRelativeTo(null);
        // Set the default close operation to exit the application
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Method to start a new game
    public void startNewGame() {
        ui.newGame(); // Start a new game in the UI
        ui.repaint(); // Repaint the UI to reflect changes
    }
    @Override
    public void endGame(boolean won) {
        // Get the game statistics for the current difficulty level
        GameStats stats = statsManager.getStats(currentDifficulty);
        if (stats != null) {       // Ensure that the game statistics are not null
            stats.recordGame(won); // Record the game result (win or loss)
        }
    }

    // Method statistics manager
    public GameStatsManager getStatsManager() {
        return statsManager;
    }
}
