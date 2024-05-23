package org.jvmlthread.javamultithreading.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.jvmlthread.javamultithreading.game.GamePlayerClass;
import java.io.IOException;

public class PlayerStatisticsView {
    private GamePlayerClass gamePlayerClass;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label playerScoreLabel;

    @FXML
    private Label playerShotsLabel;

    private VBox box;
    public PlayerStatisticsView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/jvmlthread/javamultithreading/player-stats-view.fxml"));
        fxmlLoader.setController(this);
        try
        {
            box = fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }



    public void updateFields() {
        if(gamePlayerClass != null) {
            playerNameLabel.setText(gamePlayerClass.getNickname());
            playerScoreLabel.setText(String.valueOf(gamePlayerClass.getScore()));
            playerShotsLabel.setText(String.valueOf(gamePlayerClass.getShotsCount()));
            this.getBox().setVisible(true);
        } else {
            this.getBox().setVisible(false);
        }
    }


    public void setPlayer(GamePlayerClass gamePlayerClass) {
        this.gamePlayerClass = gamePlayerClass;
        updateFields();
    }

    public VBox getBox() {
        return box;
    }
}