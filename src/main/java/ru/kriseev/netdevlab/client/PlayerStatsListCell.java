package ru.kriseev.netdevlab.client;

import javafx.scene.control.ListCell;
import ru.kriseev.netdevlab.common.model.Player;

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
