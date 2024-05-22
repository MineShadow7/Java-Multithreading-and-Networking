package ru.kriseev.netdevlab.common.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Room {
    public Room() {
        playerList = new ArrayList<>();
    }
    @Expose
    private final List<Player> playerList;

    public void addPlayer(Player player) {
        playerList.add(player);
    }
    public List<Player> getPlayers() {
        return playerList;
    }
    public void removePlayer(Player player) {
        playerList.remove(player);
    }
    public Boolean isReady() {
        if(playerList.size() < 2) {
            return false;
        }
        for(Player p : playerList) {
            if(!p.isReady()) {
                return false;
            }
        }
        return true;
    }
}
