package ru.kriseev.netdevlab.server.model;

import ru.kriseev.netdevlab.common.model.LeaderboardEntry;
import ru.kriseev.netdevlab.common.model.Player;
import ru.kriseev.netdevlab.server.dao.PlayerDAO;
import ru.kriseev.netdevlab.server.dao.PlayerDAOFactory;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepository {
    public Player getPlayer(String nickname) {
        PlayerDAO dao = PlayerDAOFactory.getInstance().getPlayerDAO();
        PlayerPersistentData data = dao.findByNickname(nickname);
        if(data == null) {
            data = new PlayerPersistentData(nickname, 0);
            dao.save(data);
        }
        return new ServerPlayer(data, this);
    }
    public List<LeaderboardEntry> getTopPlayers() {
        List<PlayerPersistentData> data = PlayerDAOFactory.getInstance().getPlayerDAO().getTopPlayers(10);
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        for(final var player : data) {
            leaderboard.add(new LeaderboardEntry(player.getNickname(), player.getRank()));
        }
        return leaderboard;
    }
    public void updatePlayer(ServerPlayer player) {
        PlayerDAOFactory.getInstance().getPlayerDAO().update(player.getPersistentData());
    }
}
