package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    // Field to store the Minesweeper instance
    private Minesweeper minesweeper;

    // Constructor to create the menu bar
    public MenuBar(Minesweeper minesweeper) {
        this.minesweeper = minesweeper;

        JMenu gameMenu = new JMenu("Game");                             // Menu "Game"
        
        JMenuItem newGameItem = new JMenuItem("New Game");           // Item "New Game"
        newGameItem.addActionListener(e -> newGame());
        gameMenu.add(newGameItem);

        JMenuItem optionsItem = new JMenuItem("Options");            // Item "Options"
        optionsItem.addActionListener(e -> showOptions());
        gameMenu.add(optionsItem);

        JMenuItem statisticsMenuItem = new JMenuItem("Statistics");  // Item "Statistics"
        statisticsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StatisticsPanel(minesweeper);
            }
        });
        gameMenu.add(statisticsMenuItem);                        // Add Statistics item to Game menu
        this.add(gameMenu);                                      // Add Game menu to MenuBar

         // Add Help menu with Tutorial item
         JMenu helpMenu = new JMenu("Help");                   // Menu "Help" 
         JMenuItem tutorialItem = new JMenuItem("Tutorial");// Item "Tutorial"
         tutorialItem.addActionListener(e -> showTutorial());
         helpMenu.add(tutorialItem);                             // Add Tutorial item to Help menu
         this.add(helpMenu);                                     // Add Help menu to MenuBar
    }

    // Method to start a new game
    private void newGame() {
        int result = JOptionPane.showConfirmDialog(null, 
                "Do you want to start a new game ?", 
                "New Game", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            minesweeper.startNewGame(); // Start a new game
                
        }
    }
    // Method to show options dialog
    private void showOptions() {
        InputPanel.getInput();          // Choose difficulty in inputpanel
        minesweeper.startNewGame();     // Start a new game
    }
    // Method to show tutorial
    private void showTutorial() {
        new TutorialFrame();            // Show tutorial class
    }
}
