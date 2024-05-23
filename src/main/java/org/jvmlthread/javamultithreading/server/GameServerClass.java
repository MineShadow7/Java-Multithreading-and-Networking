package org.jvmlthread.javamultithreading.server;


import org.jvmlthread.javamultithreading.game.GamePlayerClass;
import org.jvmlthread.javamultithreading.game.GameState;
import org.jvmlthread.javamultithreading.game.GameCircleTarget;

public class GameServerClass {
    public static GameServerClass currentGame;

    public GameState getState() {
        return state;
    }
    public final static Object gameLock = new Object();
    private final GameState state;
    public GameServerClass(double fieldWidth, double fieldHeight, GamePlayerClass[] players) {
        state = new GameState(fieldWidth, fieldHeight, new GameCircleTarget[] {
                new GameCircleTarget(20, fieldWidth * 0.66, fieldHeight / 2, fieldHeight - 40, 40, 1, 1),
                new GameCircleTarget(10, fieldWidth * 0.9, fieldHeight / 2, fieldHeight - 40, 40, 3, 2),
        }, players.length);
        for(int i = 0; i < players.length; ++i) {
            state.getPlayers()[i] = players[i];
        }
    }

    public void step() {
        for(GamePlayerClass player : state.getPlayers()) {
            if (player.getProjectile() != null) {
                player.getProjectile().step();
                if (player.getProjectile().getX() > state.getFieldWidth()) {
                    player.setProjectile(null);
                }
            }
        }
        for (GameCircleTarget target : state.getTargets()) {
            target.step();
            for(GamePlayerClass player : state.getPlayers()) {
                if (player.getProjectile() != null && target.checkHit(player.getProjectile())) {
                    player.incrementScore(target.getScoreIncrement());
                    player.setProjectile(null);
                    if(player.getScore() >= 6) {
                        this.getState().setIsFinished(true);
                        this.getState().setWinner(player);
                    }
                }
            }
        }
    }


}
