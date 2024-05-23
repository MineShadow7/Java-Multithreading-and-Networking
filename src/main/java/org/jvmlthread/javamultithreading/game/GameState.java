package org.jvmlthread.javamultithreading.game;

import com.google.gson.annotations.Expose;

public class GameState {
    public GameState(double fieldWidth, double fieldHeight, GameTarget[] gameTargets, int playerCount)
    {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        gamePlayers = new GamePlayer[playerCount];
        this.gameTargets = gameTargets;
        this.isFinished = false;
        winner = null;
        paused = false;
    }
    public GameState(double fieldWidth, double fieldHeight, GameTarget[] gameTargets, GamePlayer[] gamePlayers) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.gamePlayers = gamePlayers;
        this.gameTargets = gameTargets;
        isFinished = false;
        winner = null;
        paused = false;
    }
    public GameState(double fieldWidth, double fieldHeight, GameTarget[] gameTargets, GamePlayer[] gamePlayers, Boolean isFinished, String winner, Boolean paused) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.gamePlayers = gamePlayers;
        this.gameTargets = gameTargets;
        this.isFinished = isFinished;
        this.winner = winner;
        this.paused = paused;
    }
    @Expose
    private final GameTarget[] gameTargets;
    @Expose
    private final GamePlayer[] gamePlayers;
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
    public void setWinner(GamePlayer winner) {
        this.winner = winner.getNickname();
        winner.incrementRank();
    }
    public String getWinner() {
        return winner;
    }
    public GameTarget[] getTargets() {
        return gameTargets;
    }
    public GamePlayer[] getPlayers() {
        return gamePlayers;
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

