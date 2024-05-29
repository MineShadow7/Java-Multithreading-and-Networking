package org.jvmlthread.javamultithreading.client;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import org.jvmlthread.javamultithreading.game.GamePlayerClass;
import org.jvmlthread.javamultithreading.game.GameArrowClass;
import org.jvmlthread.javamultithreading.game.GameState;
import org.jvmlthread.javamultithreading.game.GameCircleTarget;


import java.util.ArrayList;
import java.util.List;

public class ClientRenderer {

    private final Pane gamePane;

    private Node createPlayerRectangle(GamePlayerClass gamePlayerClass) {
        double width = 50;
        double height = 30;
        javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(gamePlayerClass.getX() - width / 2, gamePlayerClass.getY() - height / 2, width, height);
        rect.setFill(Color.YELLOW);
        rect.setVisible(true);
        return rect;
    }

    public ClientRenderer(Pane gamePane) {
        this.gamePane = gamePane;
    }
    private Node createPlayerPolygon(GamePlayerClass gamePlayerClass) {
        Polygon poly = new Polygon(gamePlayerClass.getX(), gamePlayerClass.getY(), gamePlayerClass.getX() - 50, gamePlayerClass.getY() - 30,
                gamePlayerClass.getX() - 50, gamePlayerClass.getY() + 30);
        poly.setFill(Color.GREEN);
        poly.setVisible(true);
        return poly;
    }
    private void renderTargets(GameCircleTarget[] gameCircleTargets, List<Node> nodes) {
        for (GameCircleTarget t : gameCircleTargets) {
            Circle c = new Circle();
            c.setCenterX(t.getX());
            c.setCenterY(t.getY());
            c.setRadius(t.getRadius());
            c.setFill(Color.RED);
            nodes.add(c);
        }
    }
    private void renderPlayers(GamePlayerClass[] gamePlayerClasses, List<Node> nodes) {
        for (GamePlayerClass p : gamePlayerClasses) {
            Label playerName = new Label(p.getNickname());
            playerName.setLayoutX(p.getX() - 20.0);
            playerName.setLayoutY(p.getY() + 35.0);
            nodes.add(playerName);
            if (p.getProjectile() != null) {
                nodes.add(renderProjectile(p.getProjectile()));
            }
            nodes.add(createPlayerRectangle(p));
        }
    }
    private Node renderProjectile(GameArrowClass gameArrowClass) {
        Line line = new Line();
        line.setStroke(Color.BLUEVIOLET);
        line.setStartX(gameArrowClass.getX());
        line.setStartY(gameArrowClass.getY());
        line.setEndX(gameArrowClass.getX() - 45.0);
        line.setEndY(gameArrowClass.getY());
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
