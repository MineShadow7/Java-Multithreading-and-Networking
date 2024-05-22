package ru.kriseev.netdevlab.client;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import ru.kriseev.netdevlab.common.model.Arrow;
import ru.kriseev.netdevlab.common.model.GameState;
import ru.kriseev.netdevlab.common.model.Player;
import ru.kriseev.netdevlab.common.model.Target;

import java.util.ArrayList;
import java.util.List;

public class GameRenderer {

    private final Pane gamePane;


    public GameRenderer(Pane gamePane) {
        this.gamePane = gamePane;
    }

    private Node createPlayerPolygon(Player player) {
        Polygon poly = new Polygon(player.getX(), player.getY(), player.getX() - 50, player.getY() - 30,
                player.getX() - 50, player.getY() + 30);
        poly.setFill(Color.GREEN);
        poly.setVisible(true);
        return poly;
    }

    private void renderTargets(Target[] targets, List<Node> nodes) {
        for (Target t : targets) {
            Circle c = new Circle();
            c.setCenterX(t.getX());
            c.setCenterY(t.getY());
            c.setRadius(t.getRadius());
            c.setFill(Color.RED);
            nodes.add(c);
        }
    }

    private void renderPlayers(Player[] players, List<Node> nodes) {
        for (Player p : players) {
            Label playerName = new Label(p.getNickname());
            playerName.setLayoutX(p.getX() - 20.0);
            playerName.setLayoutY(p.getY() + 35.0);
            nodes.add(playerName);
            if (p.getArrow() != null) {
                nodes.add(renderArrow(p.getArrow()));
            }
            nodes.add(createPlayerPolygon(p));
        }
    }

    private Node renderArrow(Arrow arrow) {
        Line line = new Line();
        line.setStroke(Color.BLUEVIOLET);
        line.setStartX(arrow.getX());
        line.setStartY(arrow.getY());
        line.setEndX(arrow.getX() - 45.0);
        line.setEndY(arrow.getY());
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
