package ru.kriseev.netdevlab.server;

import ru.kriseev.netdevlab.common.model.GameState;
import ru.kriseev.netdevlab.common.model.Player;
import ru.kriseev.netdevlab.common.model.Target;

public class Game {
    public static Game currentGame;

    public GameState getState() {
        return state;
    }
    public final static Object gameLock = new Object();
    private final GameState state;
    public Game(double fieldWidth, double fieldHeight, Player[] players) {
        state = new GameState(fieldWidth, fieldHeight, new Target[] {
                new Target(20, fieldWidth * 0.66, fieldHeight / 2, fieldHeight - 40, 40, 1, 1),
                new Target(10, fieldWidth * 0.9, fieldHeight / 2, fieldHeight - 40, 40, 3, 2),
        }, players.length);
        for(int i = 0; i < players.length; ++i) {
            state.getPlayers()[i] = players[i];
        }
    }

    public void step() {
        for(Player player : state.getPlayers()) {
            if (player.getArrow() != null) {
                player.getArrow().step();
                if (player.getArrow().getX() > state.getFieldWidth()) {
                    player.setArrow(null);
                }
            }
        }
        for (Target target : state.getTargets()) {
            target.step();
            for(Player player : state.getPlayers()) {
                if (player.getArrow() != null && target.checkHit(player.getArrow())) {
                    player.incrementScore(target.getScoreIncrement());
                    player.setArrow(null);
                    if(player.getScore() >= 6) {
                        this.getState().setIsFinished(true);
                        this.getState().setWinner(player);
                    }
                }
            }
        }
    }


}
