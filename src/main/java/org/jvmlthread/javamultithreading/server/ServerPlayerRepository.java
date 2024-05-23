package org.jvmlthread.javamultithreading.server;

import org.jvmlthread.javamultithreading.game.GameLeaderboardEntry;
import org.jvmlthread.javamultithreading.game.GamePlayerClass;

import java.util.ArrayList;
import java.util.List;


public class ServerPlayerRepository {
    public GamePlayerClass getPlayer(String nickname) {
        PlayerDAOServerClass dao = new PlayerDAOServerClass();
        PlayerPersistentDataServer data = dao.findByNickname(nickname);
        if(data == null) {
            data = new PlayerPersistentDataServer(nickname, 0);
            dao.save(data);
        }
        return new PlayerServerClass(data, this);
    }
    public List<GameLeaderboardEntry> getTopPlayers() {
        List<PlayerPersistentDataServer> data = new PlayerDAOServerClass().getTopPlayers(10);
        List<GameLeaderboardEntry> leaderboard = new ArrayList<>();
        for(final var player : data) {
            leaderboard.add(new GameLeaderboardEntry(player.getNickname(), player.getRank()));
        }
        return leaderboard;
    }
    public void updatePlayer(PlayerServerClass player) {
        new PlayerDAOServerClass().update(player.getPersistentData());
    }
}

