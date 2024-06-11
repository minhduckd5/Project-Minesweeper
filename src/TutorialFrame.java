package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TutorialFrame extends JFrame {

    private JTextArea tutorialText;
    private int fontSize = 14; // Initial font size

    public TutorialFrame() {
        setTitle("How to Play Minesweeper");
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tutorialText = new JTextArea();
        tutorialText.setText(getTutorialText());
        tutorialText.setEditable(false); // Make the text area non-editable
        tutorialText.setLineWrap(true);  // Enable line wrapping
        tutorialText.setWrapStyleWord(true); // Wrap at word boundaries
        tutorialText.setMargin(new Insets(10, 10, 10, 10)); // Add some padding around the text
        tutorialText.setFont(new Font("Arial", Font.PLAIN, fontSize)); // Set initial font size

        JScrollPane scrollPane = new JScrollPane(tutorialText); // Add scroll pane for large text
        add(scrollPane, BorderLayout.CENTER);

        // Create Zoom In and Zoom Out buttons
        JPanel buttonPanel = new JPanel();
        JButton zoomInButton = new JButton("Zoom In");
        JButton zoomOutButton = new JButton("Zoom Out");

        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontSize += 2;
                tutorialText.setFont(new Font("Arial", Font.PLAIN, fontSize));
            }
        });

        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontSize -= 2;
                tutorialText.setFont(new Font("Arial", Font.PLAIN, fontSize));
            }
        });

        buttonPanel.add(zoomInButton);
        buttonPanel.add(zoomOutButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String getTutorialText() {
        return "How to Play Minesweeper:\n\n" +
                "1. Objective:\n" +
                "   The objective of Minesweeper is to clear a rectangular board containing hidden mines without detonating any of them.\n\n" +
                "2. Controls:\n" +
                "   - Left-click on a cell to reveal it.\n" +
                "   - Right-click on a cell to mark it as a mine.\n\n" +
                "3. Rules:\n" +
                "   - If you reveal a mine, you lose the game.\n" +
                "   - If you reveal an empty cell, it will display a number indicating how many mines are adjacent to it.\n" +
                "   - Use the numbers to deduce the locations of the mines and mark them.\n" +
                "   - Clear all non-mine cells to win the game.\n\n" +
                "4. Tips:\n" +
                "   - Start by clicking random cells. The first click is always safe.\n" +
                "   - Use right-click to mark suspected mines.\n" +
                "   - Pay attention to the numbers and use logic to avoid mines.\n" +
                "   - Practice makes perfect! Keep playing to improve your skills.\n\n" +
                "Enjoy playing Minesweeper!";
    }
}
