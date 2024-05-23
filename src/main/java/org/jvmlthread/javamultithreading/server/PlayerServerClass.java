package org.jvmlthread.javamultithreading.server;


import org.jvmlthread.javamultithreading.game.GamePlayerClass;

public class PlayerServerClass extends GamePlayerClass {
    private final ServerPlayerRepository repository;

    private final PlayerPersistentDataServer persistentData;
    public PlayerServerClass(PlayerPersistentDataServer persistentData, ServerPlayerRepository repository) {
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

    public PlayerPersistentDataServer getPersistentData() {
        return persistentData;
    }
}

