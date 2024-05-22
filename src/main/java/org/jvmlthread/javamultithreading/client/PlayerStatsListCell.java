package org.jvmlthread.javamultithreading.client;

import javafx.scene.control.ListCell;
import org.jvmlthread.javamultithreading.common.model.Player;

public class PlayerStatsListCell extends ListCell<Player> {
    PlayerStatsView view;

    public PlayerStatsListCell() {
        view = new PlayerStatsView();
        setGraphic(view.getBox());
    }

    @Override
    protected void updateItem(Player player, boolean b) {
        super.updateItem(player, b);
        view.setPlayer(player);
    }
}
