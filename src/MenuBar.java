package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuBar extends JMenuBar {
    // Field to store the Minesweeper instance
    private Minesweeper minesweeper;
    private BOT bot;

    // Constructor to create the menu bar
    public MenuBar(Minesweeper minesweeper, BOT bot) {
        this.minesweeper = minesweeper;
        this.bot = bot; // Initialize the bot instance

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
        
        JMenuItem saveGameItem = new JMenuItem("Save Game"); // Item "Save Game"
        saveGameItem.addActionListener(e -> minesweeper.saveGame());
        gameMenu.add(saveGameItem);                              // Add Save Game item to Game menu

        JMenuItem loadGameItem = new JMenuItem("Load Game"); // Item "Load Game"
        loadGameItem.addActionListener(e -> minesweeper.loadGame());
        gameMenu.add(loadGameItem);                              // Add Load Game item to Game menu
        this.add(gameMenu);                                      // Add Game menu to MenuBar


               // Add new "Play Demo" item for auto-play
        JMenuItem playDemoItem = new JMenuItem("Play Demo");
        playDemoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startBotDemo();  // Start the bot to play the game automatically
            }
        });
        gameMenu.add(playDemoItem);  // Add the new Play Demo item to the Game menu

        this.add(gameMenu);      // Add the Game menu to the MenuBar

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
        // Method to start the bot demo play
    // Method to start the bot demo play
    private void startBotDemo() {
        if (bot == null) {
            JOptionPane.showMessageDialog(null, "Bot is not initialized","Error",JOptionPane.ERROR_MESSAGE);
        }
        // Disable the menu items while the bot is playing
        setMenuItemsEnabled(false);

        // Start the bot to play the game automatically
        new Thread(() -> {
            bot.startPlaying();  // Let the bot play the game
            // Re-enable the menu items after the bot finishes playing
            SwingUtilities.invokeLater(() -> setMenuItemsEnabled(true));
        }).start();
    }

    // Helper method to enable/disable menu items during the bot's demo
    private void setMenuItemsEnabled(boolean enabled) {
        for (int i = 0; i < getMenuCount(); i++) {
            JMenu menu = getMenu(i);
            for (int j = 0; j < menu.getItemCount(); j++) {
                menu.getItem(j).setEnabled(enabled);
            }
        }
    }
}
