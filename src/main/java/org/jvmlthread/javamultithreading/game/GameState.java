package org.jvmlthread.javamultithreading.game;

import com.google.gson.annotations.Expose;

public class GameState {
    public GameState(double fieldWidth, double fieldHeight, GameCircleTarget[] gameCircleTargets, int playerCount)
    {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        gamePlayerClasses = new GamePlayerClass[playerCount];
        this.gameCircleTargets = gameCircleTargets;
        this.isFinished = false;
        winner = null;
        paused = false;
    }
    @Expose
    private final GameCircleTarget[] gameCircleTargets;
    @Expose
    private final GamePlayerClass[] gamePlayerClasses;
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
    public void setWinner(GamePlayerClass winner) {
        this.winner = winner.getNickname();
        winner.incrementRank();
    }
    public String getWinner() {
        return winner;
    }
    public GameCircleTarget[] getTargets() {
        return gameCircleTargets;
    }
    public GamePlayerClass[] getPlayers() {
        return gamePlayerClasses;
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

