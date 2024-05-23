package model;
public class User {
    private String name;
    private String password;
    private String path;

    private int bestScore;

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public int getTotalLoses() {
        return totalLoses;
    }

    public void setTotalLoses(int totalLoses) {
        this.totalLoses = totalLoses;
    }

    private int totalGames;
    private int totalWins;
    private int totalLoses;
    public User (String name, String password, String path) {
        this.name = name;
        this.password = password;
        this.path = path;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getPath() {
        return path;
    }

    public void refreshInfo(int bestScore, int totalGames, int totalWins, int totalLoses) {
        this.bestScore = bestScore;
        this.totalGames = totalGames;
        this.totalWins = totalWins;
        this.totalLoses = totalLoses;
    }
}