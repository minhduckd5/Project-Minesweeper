package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {

    private Minesweeper minesweeper;

    public MenuBar(Minesweeper minesweeper) {
        this.minesweeper = minesweeper;


        JMenu gameMenu = new JMenu("Game");
        
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(e -> newGame());
        gameMenu.add(newGameItem);

        JMenuItem optionsItem = new JMenuItem("Options");
        optionsItem.addActionListener(e -> showOptions());
        gameMenu.add(optionsItem);

        JMenuItem statisticsMenuItem = new JMenuItem("Statistics");
        statisticsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StatisticsPanel(minesweeper);
            }
        });
        gameMenu.add(statisticsMenuItem);

        this.add(gameMenu);

         // Add Help menu with Tutorial item
         JMenu helpMenu = new JMenu("Help");
         JMenuItem tutorialItem = new JMenuItem("Tutorial");
         tutorialItem.addActionListener(e -> showTutorial());
         helpMenu.add(tutorialItem);
         this.add(helpMenu);
    }

    private void newGame() {
        int result = JOptionPane.showConfirmDialog(null, 
                "Do you want to start a new game ?", 
                "New Game", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            minesweeper.startNewGame(); // Start a new game
                
        }
    }

    private void showOptions() {
        InputPanel.getInput();
        minesweeper.startNewGame();
    }

    private void showTutorial() {
        new TutorialFrame();
    }
}
