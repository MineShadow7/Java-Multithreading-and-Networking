package ru.kriseev.netdevlab.server.dao;

import ru.kriseev.netdevlab.server.model.PlayerPersistentData;

import java.util.List;

public interface PlayerDAO {
    PlayerPersistentData findByNickname(String nickname);
    PlayerPersistentData findById(int id);
    List<PlayerPersistentData> getTopPlayers(int count);
    void save(PlayerPersistentData player);
    void update(PlayerPersistentData player);
}
