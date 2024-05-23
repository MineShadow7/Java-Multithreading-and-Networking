package org.jvmlthread.javamultithreading.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.jvmlthread.javamultithreading.game.GamePlayer;
import java.io.IOException;

public class ClientPlayerStatsView {
    private GamePlayer gamePlayer;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label playerScoreLabel;

    @FXML
    private Label playerShotsLabel;

    private VBox box;
    public ClientPlayerStatsView() {
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
        if(gamePlayer != null) {
            playerNameLabel.setText(gamePlayer.getNickname());
            playerScoreLabel.setText(String.valueOf(gamePlayer.getScore()));
            playerShotsLabel.setText(String.valueOf(gamePlayer.getShotsCount()));
            this.getBox().setVisible(true);
        } else {
            this.getBox().setVisible(false);
        }
    }


    public void setPlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        updateFields();
    }

    public VBox getBox() {
        return box;
    }
}