package src;

public class GameStatsManager {
    // Fields to store statistics for each difficulty level
    private GameStats beginnerStats;
    private GameStats intermediateStats;
    private GameStats advancedStats;

    // Constructor initializes statistics for each difficulty level
    public GameStatsManager() {
        beginnerStats = new GameStats("game_stats_beginner.txt");
        intermediateStats = new GameStats("game_stats_intermediate.txt");
        advancedStats = new GameStats("game_stats_advanced.txt");
    }

    // Method to get statistics for a specific difficulty level
    public GameStats getStats(String difficulty) {
        switch (difficulty) {
            case "Beginner":
                return beginnerStats;
            case "Intermediate":
                return intermediateStats;
            case "Advanced":
                return advancedStats;
            default:
                return null; // Return null for custom or unknown difficulties
        }
    }

    // Method to save all statistics
    public void saveAllStats() {
        beginnerStats.saveToFile();
        intermediateStats.saveToFile();
        advancedStats.saveToFile();
    }
    // Method to reset all statistics
    public void resetAllStats() {
        beginnerStats.reset();
        intermediateStats.reset();
        advancedStats.reset();
        saveAllStats();
    }
}
