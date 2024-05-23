package org.jvmlthread.javamultithreading.client;

import javafx.scene.control.ListCell;
import org.jvmlthread.javamultithreading.game.GamePlayerClass;


public class PlayerStatisticsList extends ListCell<GamePlayerClass> {
    PlayerStatisticsView view;

    public PlayerStatisticsList() {
        view = new PlayerStatisticsView();
        setGraphic(view.getBox());
    }

    @Override
    protected void updateItem(GamePlayerClass gamePlayerClass, boolean b) {
        super.updateItem(gamePlayerClass, b);
        view.setPlayer(gamePlayerClass);
    }
}
