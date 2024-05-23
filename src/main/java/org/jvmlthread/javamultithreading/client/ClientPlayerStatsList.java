package org.jvmlthread.javamultithreading.client;

import javafx.scene.control.ListCell;
import org.jvmlthread.javamultithreading.game.GamePlayer;


public class ClientPlayerStatsList extends ListCell<GamePlayer> {
    ClientPlayerStatsView view;

    public ClientPlayerStatsList() {
        view = new ClientPlayerStatsView();
        setGraphic(view.getBox());
    }

    @Override
    protected void updateItem(GamePlayer gamePlayer, boolean b) {
        super.updateItem(gamePlayer, b);
        view.setPlayer(gamePlayer);
    }
}
