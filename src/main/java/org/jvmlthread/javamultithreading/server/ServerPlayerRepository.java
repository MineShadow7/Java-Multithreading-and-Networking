package org.jvmlthread.javamultithreading.server;

import org.jvmlthread.javamultithreading.game.GameLeaderboardEntry;
import org.jvmlthread.javamultithreading.game.GamePlayer;

import java.util.ArrayList;
import java.util.List;


public class ServerPlayerRepository {
    public GamePlayer getPlayer(String nickname) {
        ServerPlayerDAO dao = new ServerPlayerDAO();
        ServerPlayerPersistentData data = dao.findByNickname(nickname);
        if(data == null) {
            data = new ServerPlayerPersistentData(nickname, 0);
            dao.save(data);
        }
        return new ServerPlayer(data, this);
    }
    public List<GameLeaderboardEntry> getTopPlayers() {
        List<ServerPlayerPersistentData> data = new ServerPlayerDAO().getTopPlayers(10);
        List<GameLeaderboardEntry> leaderboard = new ArrayList<>();
        for(final var player : data) {
            leaderboard.add(new GameLeaderboardEntry(player.getNickname(), player.getRank()));
        }
        return leaderboard;
    }
    public void updatePlayer(ServerPlayer player) {
        new ServerPlayerDAO().update(player.getPersistentData());
    }
}

