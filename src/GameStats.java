package src;

import java.io.*;
import java.nio.file.*;

public class GameStats {
    // Fields to store statistics
    private int gamesPlayed;
    private int gamesWon;
    private int longestWinningStreak;
    private int longestLosingStreak;
    private int currentStreak;
    private int currentStreakLength;
    private String filename;

    // Constructor initializes fields to zero and loads saved stats if available
    public GameStats(String filename) {
        this.filename = filename;
        loadFromFile();
    }

    // Method to record the result of a game
    public void recordGame(boolean won) {
        gamesPlayed++;
        if (won) {
            gamesWon++;
            if (currentStreak >= 0) {
                currentStreak++;
                currentStreakLength++;
            } else {
                currentStreak = 1;
                currentStreakLength = 1;
            }
            if (currentStreakLength > longestWinningStreak) {
                longestWinningStreak = currentStreakLength;
            }
        } else {
            if (currentStreak <= 0) {
                currentStreak--;
                currentStreakLength++;
            } else {
                currentStreak = -1;
                currentStreakLength = 1;
            }
            if (currentStreakLength > longestLosingStreak) {
                longestLosingStreak = currentStreakLength;
            }
        }
        saveToFile();
    }

    // Getter for gamesPlayed
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    // Getter for gamesWon
    public int getGamesWon() {
        return gamesWon;
    }

    // Method to calculate win percentage
    public double getWinPercentage() {
        if (gamesPlayed == 0) return 0;
        return (double) gamesWon / gamesPlayed * 100;
    }

    // Getter for longestWinningStreak
    public int getLongestWinningStreak() {
        return longestWinningStreak;
    }

    // Getter for longestLosingStreak
    public int getLongestLosingStreak() {
        return longestLosingStreak;
    }

    // Getter for currentStreak
    public int getCurrentStreak() {
        return currentStreak;
    }

    // Method to save statistics to a text file
    public void saveToFile() {
        String directoryPath = "src/gameSave/";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directory if it doesn't exist
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(directoryPath + filename))) {
            writer.write("Games Played: " + gamesPlayed);
            writer.newLine();
            writer.write("Games Won: " + gamesWon);
            writer.newLine();
            writer.write("Win Percentage: " + getWinPercentage());
            writer.newLine();
            writer.write("Longest Winning Streak: " + longestWinningStreak);
            writer.newLine();
            writer.write("Longest Losing Streak: " + longestLosingStreak);
            writer.newLine();
            writer.write("Current Streak: " + currentStreak);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load statistics from a text file
    public void loadFromFile() {
        String directoryPath = "src/gameSave/";
        Path path = Paths.get(directoryPath + filename);
        if (Files.exists(path)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
                gamesPlayed = Integer.parseInt(reader.readLine().split(": ")[1]);
                gamesWon = Integer.parseInt(reader.readLine().split(": ")[1]);
                reader.readLine(); // Skip win percentage line
                longestWinningStreak = Integer.parseInt(reader.readLine().split(": ")[1]);
                longestLosingStreak = Integer.parseInt(reader.readLine().split(": ")[1]);
                currentStreak = Integer.parseInt(reader.readLine().split(": ")[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void reset() {
        gamesPlayed = 0;
        gamesWon = 0;
        longestWinningStreak = 0;
        longestLosingStreak = 0;
        currentStreak = 0;
        currentStreakLength = 0;
    }
}
