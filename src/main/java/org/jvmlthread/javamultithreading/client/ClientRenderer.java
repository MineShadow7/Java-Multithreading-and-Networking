package org.jvmlthread.javamultithreading.client;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import org.jvmlthread.javamultithreading.game.GamePlayer;
import org.jvmlthread.javamultithreading.game.GameProjectile;
import org.jvmlthread.javamultithreading.game.GameState;
import org.jvmlthread.javamultithreading.game.GameTarget;


import java.util.ArrayList;
import java.util.List;

public class ClientRenderer {

    private final Pane gamePane;


    public ClientRenderer(Pane gamePane) {
        this.gamePane = gamePane;
    }
    private Node createPlayerPolygon(GamePlayer gamePlayer) {
        Polygon poly = new Polygon(gamePlayer.getX(), gamePlayer.getY(), gamePlayer.getX() - 50, gamePlayer.getY() - 30,
                gamePlayer.getX() - 50, gamePlayer.getY() + 30);
        poly.setFill(Color.GREEN);
        poly.setVisible(true);
        return poly;
    }
    private void renderTargets(GameTarget[] gameTargets, List<Node> nodes) {
        for (GameTarget t : gameTargets) {
            Circle c = new Circle();
            c.setCenterX(t.getX());
            c.setCenterY(t.getY());
            c.setRadius(t.getRadius());
            c.setFill(Color.RED);
            nodes.add(c);
        }
    }
    private void renderPlayers(GamePlayer[] gamePlayers, List<Node> nodes) {
        for (GamePlayer p : gamePlayers) {
            Label playerName = new Label(p.getNickname());
            playerName.setLayoutX(p.getX() - 20.0);
            playerName.setLayoutY(p.getY() + 35.0);
            nodes.add(playerName);
            if (p.getProjectile() != null) {
                nodes.add(renderProjectile(p.getProjectile()));
            }
            nodes.add(createPlayerPolygon(p));
        }
    }
    private Node renderProjectile(GameProjectile gameProjectile) {
        Line line = new Line();
        line.setStroke(Color.BLUEVIOLET);
        line.setStartX(gameProjectile.getX());
        line.setStartY(gameProjectile.getY());
        line.setEndX(gameProjectile.getX() - 45.0);
        line.setEndY(gameProjectile.getY());
        return line;
    }
    public void renderState(GameState state) {
        if (state == null) {
            return;
        }
        ArrayList<Node> nodes = new ArrayList<>();
        renderTargets(state.getTargets(), nodes);
        renderPlayers(state.getPlayers(), nodes);
        gamePane.getChildren().clear();
        gamePane.getChildren().addAll(nodes);
    }
    public void clearGame() {
        gamePane.getChildren().clear();
    }
}
