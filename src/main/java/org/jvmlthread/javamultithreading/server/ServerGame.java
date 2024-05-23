package org.jvmlthread.javamultithreading.server;


import org.jvmlthread.javamultithreading.game.GamePlayer;
import org.jvmlthread.javamultithreading.game.GameState;
import org.jvmlthread.javamultithreading.game.GameTarget;

public class ServerGame {
    public static ServerGame currentGame;

    public GameState getState() {
        return state;
    }
    public final static Object gameLock = new Object();
    private final GameState state;
    public ServerGame(double fieldWidth, double fieldHeight, GamePlayer[] players) {
        state = new GameState(fieldWidth, fieldHeight, new GameTarget[] {
                new GameTarget(20, fieldWidth * 0.66, fieldHeight / 2, fieldHeight - 40, 40, 1, 1),
                new GameTarget(10, fieldWidth * 0.9, fieldHeight / 2, fieldHeight - 40, 40, 3, 2),
        }, players.length);
        for(int i = 0; i < players.length; ++i) {
            state.getPlayers()[i] = players[i];
        }
    }

    public void step() {
        for(GamePlayer player : state.getPlayers()) {
            if (player.getProjectile() != null) {
                player.getProjectile().step();
                if (player.getProjectile().getX() > state.getFieldWidth()) {
                    player.setProjectile(null);
                }
            }
        }
        for (GameTarget target : state.getTargets()) {
            target.step();
            for(GamePlayer player : state.getPlayers()) {
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
