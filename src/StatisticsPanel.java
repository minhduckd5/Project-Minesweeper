package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;

public class StatisticsPanel extends JFrame {

    private JRadioButton easyButton;
    private JRadioButton mediumButton;
    private JRadioButton hardButton;
    private JTextArea statsTextArea;

    public StatisticsPanel() {
        setTitle("Statistics");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new FlowLayout());

        easyButton = new JRadioButton("Beginner");
        mediumButton = new JRadioButton("Intermediate");
        hardButton = new JRadioButton("Advanced");

        ButtonGroup difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyButton);
        difficultyGroup.add(mediumButton);
        difficultyGroup.add(hardButton);

        difficultyPanel.add(easyButton);
        difficultyPanel.add(mediumButton);
        difficultyPanel.add(hardButton);

        add(difficultyPanel, BorderLayout.NORTH);

        statsTextArea = new JTextArea();
        add(new JScrollPane(statsTextArea), BorderLayout.CENTER);

        JButton showStatsButton = new JButton("Show Statistics");
        showStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStatistics();
            }
        });
        add(showStatsButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void showStatistics() {
        String difficulty = "";
        String path = "";
    
        if (easyButton.isSelected()) {
            difficulty = "Beginner";
            path = "game_stats_beginner";
        } else if (mediumButton.isSelected()) {
            difficulty = "Intermediate";
            path = "game_stats_intermediate";
        } else if (hardButton.isSelected()) {
            difficulty = "Advanced";
            path = "game_stats_advanced";
        }
    
        if (!difficulty.isEmpty()) {
            loadStatistics(difficulty, path);
        }
    }
    private void loadStatistics(String difficulty, String path) {
        try (FileReader reader = new FileReader("src/gameSave/" + path + ".txt")) {
            // Read the file and display the statistics based on difficulty
            // This is a placeholder, the actual implementation may vary
            char[] buffer = new char[1024];
            int numCharsRead = reader.read(buffer);
            String fileContent = new String(buffer, 0, numCharsRead);
            // Parse the fileContent based on the difficulty
            // For now, we just display the content
            statsTextArea.setText("Difficulty: " + difficulty + "\n" + fileContent);
        } catch (IOException e) {
            statsTextArea.setText("Failed to load statistics for " + difficulty);
        }
    }   
}
