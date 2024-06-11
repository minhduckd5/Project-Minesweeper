package src;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Minesweeper extends JFrame implements GameEndListener {
    private UI ui;
    private final JLabel status;
    private GameStatsManager statsManager;
    private String currentDifficulty;

    public Minesweeper() {

    	status = new JLabel("");
        InputPanel inputPanel = new InputPanel();
        inputPanel.getInput();

        // Create the menu bar
        MenuBar menuBar = new MenuBar(this); //pass 'this' to menbar
        // Add the menu bar to the frame
        setJMenuBar(menuBar);

        ui =new UI(this,status);
        add(status, BorderLayout.SOUTH);
        add(ui);


        setResizable(true);
        pack();

        setTitle("Minesweeper");

        statsManager = new GameStatsManager();
        //get difficulty from input panel
        currentDifficulty = inputPanel.getSelectedDifficulty();


        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void startNewGame(){
        ui.newGame();
        ui.repaint();
    }

    @Override
    public void endGame(boolean won) {
        statsManager.getStats(currentDifficulty).recordGame(won);
        
    }

    public GameStatsManager getStatsManager() {
        return statsManager;
    }
}
