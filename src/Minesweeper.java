package src;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Minesweeper extends JFrame {
    private final JLabel status;

    public Minesweeper() {
    	status = new JLabel("");
        InputPanel inputPanel = new InputPanel();
        inputPanel.getInput();

        // Create the menu bar
        MenuBar menuBar = new MenuBar();
        // Add the menu bar to the frame
        setJMenuBar(menuBar);

        add(status, BorderLayout.SOUTH);
        add(new UI(status));
        setResizable(true);
        pack();
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
