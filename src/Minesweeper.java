package src;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Minesweeper extends JFrame {
    private final JLabel status;

    public Minesweeper() {
    	status = new JLabel("");

        // javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        // this.setLayout(layout);
        // layout.setHorizontalGroup(
        //     layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        //     .addGroup(layout.createSequentialGroup()
        //         .addGap(205, 205, 205)
        //         .addComponent(status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        //         .addGap(158, 158, 158))
        // );
        // layout.setVerticalGroup(
        //     layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        //     .addGroup(layout.createSequentialGroup()
        //         .addGap(204, 204, 204)
        //         .addComponent(status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        //         .addGap(80, 80, 80))
        // );

        add(status, BorderLayout.SOUTH);
        add(new UI(status));
        setResizable(true);
        pack();
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
     	Minesweeper ms = new Minesweeper();
    	ms.setVisible(true);
    }
}
