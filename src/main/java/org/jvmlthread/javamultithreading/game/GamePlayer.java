package org.jvmlthread.javamultithreading.game;
import com.google.gson.annotations.Expose;

public class GamePlayer {
    @Expose
    private final int id;
    @Expose
    private final String nickname;
    @Expose
    private double x;
    @Expose
    private double y;
    @Expose
    private int score = 0;
    @Expose
    private int shotsCount = 0;
    @Expose
    private Boolean ready = false;

    @Expose
    private int rank = 0;

    @Expose
    private GameProjectile gameProjectile;

    public GamePlayer(int id, double x, double y, String nickname, int rank) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.nickname = nickname;
        this.rank = rank;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public int getScore() {
        return score;
    }
    public int getShotsCount() {
        return shotsCount;
    }
    public double getY() {
        return y;
    }
    public void incrementShots() {
        shotsCount++;
    }
    public int getRank() {
        return rank;
    }
    public void incrementRank() {
        setRank(rank + 1);
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    public void incrementScore(int increment) {
        score += increment;
    }
    public int getId() {
        return id;
    }
    public GameProjectile getProjectile() {
        return gameProjectile;
    }
    public void setProjectile(GameProjectile gameProjectile) {
        this.gameProjectile = gameProjectile;
    }
    public void shoot() {
        if(gameProjectile != null) {
            return;
        }
        gameProjectile = new GameProjectile(x, y, 9);
        incrementShots();
    }
    public Boolean isReady() {
        return ready;
    }
    public void setReady(Boolean ready) {
        this.ready = ready;
    }
    public String getNickname() {
        return nickname;
    }
    public void reset() {
        score = 0;
        shotsCount = 0;
    }
}
