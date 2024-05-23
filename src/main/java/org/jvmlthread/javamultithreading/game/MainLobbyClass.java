package org.jvmlthread.javamultithreading.game;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class MainLobbyClass {
    public MainLobbyClass() {
        gamePlayerClassList = new ArrayList<>();
    }
    @Expose
    private final List<GamePlayerClass> gamePlayerClassList;

    public void addPlayer(GamePlayerClass gamePlayerClass) {
        gamePlayerClassList.add(gamePlayerClass);
    }
    public List<GamePlayerClass> getPlayers() {
        return gamePlayerClassList;
    }
    public void removePlayer(GamePlayerClass gamePlayerClass) {
        gamePlayerClassList.remove(gamePlayerClass);
    }
    public Boolean isReady() {
        if(gamePlayerClassList.size() < 2) {
            return false;
        }
        for(GamePlayerClass p : gamePlayerClassList) {
            if(!p.isReady()) {
                return false;
            }
        }
        return true;
    }
}
