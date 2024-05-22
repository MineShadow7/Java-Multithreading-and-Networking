package ru.kriseev.netdevlab.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class LeaderboardDialog {
    @FXML
    private TextArea leaderboardText;
    @FXML
    private Button refreshButton;
    private final GameClient client;
    public LeaderboardDialog(GameClient client) {
        this.client = client;
    }

    public void initialize() {
        refreshButton.setOnAction((e) -> refresh());
        refresh();
    }

    public void refresh() {
        if(client == null) {
            return;
        }
        synchronized (client) {
            var leaderboard = client.getLeaderboard();
            leaderboardText.setText("Leaderboard: ");
            for(var entry : leaderboard) {
                leaderboardText.setText(leaderboardText.getText() + "\n" + entry.playerName() + ": " + entry.rank() + " wins");
            }
        }
    }
}
