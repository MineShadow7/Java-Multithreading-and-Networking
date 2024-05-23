package org.jvmlthread.javamultithreading.server;


import org.jvmlthread.javamultithreading.game.GamePlayer;

public class ServerPlayer extends GamePlayer {
    private final ServerPlayerRepository repository;

    private final ServerPlayerPersistentData persistentData;
    public ServerPlayer(ServerPlayerPersistentData persistentData, ServerPlayerRepository repository) {
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

    public ServerPlayerPersistentData getPersistentData() {
        return persistentData;
    }
}

