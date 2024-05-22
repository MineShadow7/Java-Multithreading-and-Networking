package ru.kriseev.netdevlab.common.model;

import com.google.gson.annotations.Expose;

public class GameState {
    public GameState(double fieldWidth, double fieldHeight, Target[] targets, int playerCount)
    {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        players = new Player[playerCount];
        this.targets = targets;
        this.isFinished = false;
        winner = null;
        paused = false;
    }
    public GameState(double fieldWidth, double fieldHeight, Target[] targets, Player[] players) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.players = players;
        this.targets = targets;
        isFinished = false;
        winner = null;
        paused = false;
    }
    public GameState(double fieldWidth, double fieldHeight, Target[] targets, Player[] players, Boolean isFinished, String winner, Boolean paused) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.players = players;
        this.targets = targets;
        this.isFinished = isFinished;
        this.winner = winner;
        this.paused = paused;
    }
    @Expose
    private final Target[] targets;
    @Expose
    private final Player[] players;
    @Expose
    private final double fieldWidth;
    @Expose
    private final double fieldHeight;
    @Expose

    private String winner;
    @Expose

    private Boolean isFinished;

    @Expose
    private Boolean paused;

    public void setIsFinished(Boolean value) {
        isFinished = value;
    }
    public Boolean getIsFinished() {
        return isFinished;
    }
    public void setWinner(Player winner) {
        this.winner = winner.getNickname();
        winner.incrementRank();
    }
    public String getWinner() {
        return winner;
    }
    public Target[] getTargets() {
        return targets;
    }

    public Player[] getPlayers() {
        return players;
    }

    public double getFieldWidth() {
        return fieldWidth;
    }

    public double getFieldHeight() {
        return fieldHeight;
    }
    public Boolean getPaused() {
        return paused;
    }
    public void setPaused(Boolean paused) {
        this.paused = paused;
    }
}
