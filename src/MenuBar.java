package src;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    private static int width;
    private static int height;
    private static int mines;

    public MenuBar() {


        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(e -> newGame());
        gameMenu.add(newGameItem);
        this.add(gameMenu);
    }

    private void newGame() {
        int result = JOptionPane.showConfirmDialog(null, 
                "Do you want to start a new game with current settings (Width: " + width + ", Height: " + height + ", Mines: " + mines + ")?", 
                "New Game", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            // Code to start a new game goes here
            System.out.println("Starting a new game...");
        }
    }
}
