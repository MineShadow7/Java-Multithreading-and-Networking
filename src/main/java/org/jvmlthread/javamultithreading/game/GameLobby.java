package org.jvmlthread.javamultithreading.game;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class GameLobby {
    public GameLobby() {
        gamePlayerList = new ArrayList<>();
    }
    @Expose
    private final List<GamePlayer> gamePlayerList;

    public void addPlayer(GamePlayer gamePlayer) {
        gamePlayerList.add(gamePlayer);
    }
    public List<GamePlayer> getPlayers() {
        return gamePlayerList;
    }
    public void removePlayer(GamePlayer gamePlayer) {
        gamePlayerList.remove(gamePlayer);
    }
    public Boolean isReady() {
        if(gamePlayerList.size() < 2) {
            return false;
        }
        for(GamePlayer p : gamePlayerList) {
            if(!p.isReady()) {
                return false;
            }
        }
        return true;
    }
}
