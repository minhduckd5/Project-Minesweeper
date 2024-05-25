package src;

import javax.swing.*;

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

        this.add(gameMenu);
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
}
