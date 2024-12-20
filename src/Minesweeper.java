package src;

import java.awt.BorderLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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

        // Prompt to save the game on close
        addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            int result = JOptionPane.showConfirmDialog(null, "Do you want to save the game before exiting?", "Save Game", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                saveGame();
            }
            System.exit(0);
        }
    });
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

    // Method to save the game state
    public void saveGame() {
        String filePath = "src/gameSave/saved_game.dat";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Create a HashMap to store the game state
            Map<String, Object> gameState = new HashMap<>();
            // Put the current game state values into the HashMap
            gameState.put("field", ui.getField());
            gameState.put("inGame", ui.isInGame());
            gameState.put("minesLeft", ui.getMinesLeft());
            gameState.put("firstClick", ui.isFirstClick());
            // Write the HashMap to the file
            out.writeObject(gameState);
            // Update the status to indicate the game has been saved
            status.setText("Game Saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load the game state
    public void loadGame() {
        String filePath = "src/gameSave/saved_game.dat";
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            // Read the HashMap containing the game state from the file
            Map<String, Object> gameState = (Map<String, Object>) in.readObject();
            // Retrieve the game state values from the HashMap
            ui.setField((int[]) gameState.get("field"));
            ui.setInGame((boolean) gameState.get("inGame"));
            ui.setMinesLeft((int) gameState.get("minesLeft"));
            ui.setFirstClick((boolean) gameState.get("firstClick"));
            // Update the status label with the number of mines left
            status.setText(Integer.toString(ui.getMinesLeft()));
            // Repaint the UI with the loaded game state
            ui.repaint();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            // Update the status to indicate the game failed to load
            status.setText("Failed to Load Game");
        }
    }
}
