package src;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Minesweeper extends JFrame {
    private UI ui;
    private final JLabel status;

    public Minesweeper() {
    	status = new JLabel("");
        InputPanel inputPanel = new InputPanel();
        inputPanel.getInput();

        // Create the menu bar
        MenuBar menuBar = new MenuBar(this); //pass 'this' to menbar
        // Add the menu bar to the frame
        setJMenuBar(menuBar);

        ui =new UI(status);
        add(status, BorderLayout.SOUTH);
        add(ui);
        setResizable(true);
        pack();
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void startNewGame(){
        ui.newGame();
        ui.repaint();
    }
}
