package src;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar() {


        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(e -> newGame());
        gameMenu.add(newGameItem);
        this.add(gameMenu);
    }

    private void newGame() {
        int result = JOptionPane.showConfirmDialog(null, 
                "Do you want to start a new game ?", 
                "New Game", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
                
        }
    }
}
