package org.jvmlthread.javamultithreading.server.dao.model;

import org.jvmlthread.javamultithreading.common.model.Player;

public class ServerPlayer extends Player {
    private final PlayerRepository repository;

    private final PlayerPersistentData persistentData;
    public ServerPlayer(PlayerPersistentData persistentData, PlayerRepository repository) {
        super(persistentData.getId(), 75.0, 105.0, persistentData.getNickname(), persistentData.getRank());
        this.persistentData = persistentData;
        this.repository = repository;
    }
    @Override
    public void setRank(int rank) {
        super.setRank(rank);
        persistentData.setRank(rank);
        repository.updatePlayer(this);
    }

    public PlayerPersistentData getPersistentData() {
        return persistentData;
    }
}
