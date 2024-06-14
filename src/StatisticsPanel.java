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
    private Minesweeper minesweeper;
    private String currentDifficulty; // Store the currently selected difficulty

    public StatisticsPanel(Minesweeper minesweeper) {
        this.minesweeper = minesweeper;
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
        statsTextArea.setEditable(false);
        add(new JScrollPane(statsTextArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // JButton showStatsButton = new JButton("Show Statistics");
        // showStatsButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         showStatistics();
        //     }
        // });
        // add(showStatsButton, BorderLayout.EAST);

        JButton resetStatsButton = new JButton("Reset Statistics");
        resetStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetStatistics();
            }
        });
        buttonPanel.add(resetStatsButton);

        add(buttonPanel, BorderLayout.SOUTH);

        //  // Add action listeners to radio buttons to show statistics when selected
        //  easyButton.addActionListener(e -> showStatistics("Beginner", "game_stats_beginner"));
        //  mediumButton.addActionListener(e -> showStatistics("Intermediate", "game_stats_intermediate"));
        //  hardButton.addActionListener(e -> showStatistics("Advanced", "game_stats_advanced"));
        // Add action listeners to radio buttons to show statistics when selected
        easyButton.addActionListener(e -> {
            currentDifficulty = "Beginner";
            showStatistics(currentDifficulty, "game_stats_beginner");
        });
        mediumButton.addActionListener(e -> {
            currentDifficulty = "Intermediate";
            showStatistics(currentDifficulty, "game_stats_intermediate");
        });
        hardButton.addActionListener(e -> {
            currentDifficulty = "Advanced";
            showStatistics(currentDifficulty, "game_stats_advanced");
        });

        setVisible(true);
    }

    // private void showStatistics() {
    //     String difficulty = "";
    //     String path = "";
    
    //     if (easyButton.isSelected()) {
    //         difficulty = "Beginner";
    //         path = "game_stats_beginner";
    //     } else if (mediumButton.isSelected()) {
    //         difficulty = "Intermediate";
    //         path = "game_stats_intermediate";
    //     } else if (hardButton.isSelected()) {
    //         difficulty = "Advanced";
    //         path = "game_stats_advanced";
    //     }
    
    //     if (!difficulty.isEmpty()) {
    //         loadStatistics(difficulty, path);
    //     }
    // }
   
    // private void loadStatistics(String difficulty, String path) {
    //     try (FileReader reader = new FileReader("src/gameSave/" + path + ".txt")) {
    //         // Read the file and display the statistics based on difficulty
    //         // This is a placeholder, the actual implementation may vary
    //         char[] buffer = new char[1024];
    //         int numCharsRead = reader.read(buffer);
    //         String fileContent = new String(buffer, 0, numCharsRead);
    //         // Parse the fileContent based on the difficulty
    //         // For now, we just display the content
    //         statsTextArea.setText("Difficulty: " + difficulty + "\n" + fileContent);
    //     } catch (IOException e) {
    //         statsTextArea.setText("Failed to load statistics for " + difficulty);
    //     }
    // }   

    private void showStatistics(String difficulty, String path) {
        try (FileReader reader = new FileReader("src/gameSave/" + path + ".txt")) {
            // Read the file and display the statistics based on difficulty
            char[] buffer = new char[1024];
            int numCharsRead = reader.read(buffer);
            String fileContent = new String(buffer, 0, numCharsRead);
            statsTextArea.setText("Difficulty: " + difficulty + "\n" + fileContent);
        } catch (IOException e) {
            statsTextArea.setText("Failed to load statistics for " + difficulty);
        }
    }

    private void resetStatistics() {
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to reset all statistics?", 
                "Reset Statistics", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            GameStatsManager statsManager = minesweeper.getStatsManager();
            statsManager.resetAllStats();
            
            statsTextArea.setText("Statistics have been reset.");

        // Wait for 2 seconds and then show the statistics again for the current difficulty
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentDifficulty != null) {
                    switch (currentDifficulty) {
                        case "Beginner":
                            showStatistics("Beginner", "game_stats_beginner");
                            break;
                        case "Intermediate":
                            showStatistics("Intermediate", "game_stats_intermediate");
                            break;
                        case "Advanced":
                            showStatistics("Advanced", "game_stats_advanced");
                            break;
                    }
                }
            }
        });
        timer.setRepeats(false); // Ensure the timer only runs once
        timer.start();
        }
    }
}
