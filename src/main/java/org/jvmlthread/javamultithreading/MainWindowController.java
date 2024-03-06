package org.jvmlthread.javamultithreading;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;


public class MainWindowController {

    @FXML
    public Pane Pane2;
    @FXML
    public Pane Pane1;
    @FXML
    public Button StartButton;
    @FXML
    public Circle target1;
    @FXML
    public Circle target2;
    @FXML
    public ImageView Player;
    public Button pauseButton;
    public Label ScoreLabel;
    private int score = 0;

    private boolean gameRunning = false;
    private ExecutorService executorService;
    private AtomicBoolean isPaused;

    public void initialize() {
        isPaused = new AtomicBoolean(false);
        Player.setImage(new Image("file:Assets/Stickman/Idle/Idle.png"));
    }

    public void OnPane1MouseClick(MouseEvent mouseEvent) {
    }

    public void OnStartButtonClick(ActionEvent actionEvent) {
        if (!gameRunning) {
            gameRunning = true;
            startGame();
        }
    }

    public void OnPane3MouseClick(MouseEvent mouseEvent) {
        Player.setLayoutX(mouseEvent.getX());
        Player.setLayoutY(mouseEvent.getY());
    }

    public void onShootbtnClick(ActionEvent actionEvent) {
        if (gameRunning) {
            shootArrow();
        }
    }

    private void startGame() {
        Pane1.getChildren().clear();
        Pane1.getChildren().addAll(target1, target2);
        score = 0;
        executorService = Executors.newFixedThreadPool(2);
        executorService.submit(this::updateTargets);
    }
    private void updateTargets() {
        final int[] directiont1 = {1}; // Направление движения мишени 1 (вверх)
        final int[] directiont2 = {-1}; // Направление движения мишени 2 (вниз)
        double panelHeight = Pane1.getHeight(); // Высота панели

        while (gameRunning && !Thread.currentThread().isInterrupted()) {
            if (!isPaused.get()) {
                Platform.runLater(() -> {
                    // Обновление позиции мишени 1
                    double newY1 = target1.getCenterY() + 5 * directiont1[0];
                    if (newY1 < 0 || newY1 > panelHeight) {
                        directiont1[0] = -directiont1[0]; // Изменение направления движения
                    }
                    target1.setCenterY(newY1);

                    // Обновление позиции мишени 2
                    double newY2 = target2.getCenterY() - 5 * directiont2[0];
                    if (newY2 < 0 || newY2 > panelHeight) {
                        directiont2[0] = -directiont2[0]; // Изменение направления движения
                    }
                    target2.setCenterY(newY2);
                });
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    private void shootArrow() {
        Circle arrow = new Circle(5, 100, 5, Color.BLACK);
        Pane1.getChildren().add(arrow); // Добавляем стрелу на gamePane
        executorService.submit(() -> animateArrow(arrow));
    }

    private void animateArrow(Circle arrow) {
        final boolean[] notHitorOut = {true};
        if (!isPaused.get()) {
            while (notHitorOut[0]) {
                Platform.runLater(() -> {
                    arrow.setCenterX(arrow.getCenterX() + 50); // Стрела движется вправо
                    // Проверяем, не вышел ли стрела за пределы панели
                    if (arrow.getCenterX() > Pane1.getWidth()) {
                        Platform.runLater(() -> Pane1.getChildren().remove(arrow));
                        System.out.println("Out!");
                        notHitorOut[0] = false;
                    }
                    if (checkHit(arrow)) {
                        Platform.runLater(() -> Pane1.getChildren().remove(arrow));
                        notHitorOut[0] = false;
                    }
                    ;
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private boolean checkHit(Circle arrow) {
        if (arrow.getBoundsInParent().intersects(target1.getBoundsInParent())) {
            score += 1;
            Platform.runLater(() -> {
                System.out.println("Hit!");
                ScoreLabel.setText(String.valueOf(score));
            });
            return true;
        } else if (arrow.getBoundsInParent().intersects(target2.getBoundsInParent())) {
            score += 2;
            Platform.runLater(() -> {
                System.out.println("Hit!");
                ScoreLabel.setText(String.valueOf(score));
            });
            return true;
        }else{
            ScoreLabel.setText(String.valueOf(score));
            return false;
        }

    }
    public void OnPauseBtnClick(ActionEvent actionEvent) {
        isPaused.set(!isPaused.get());
    }

    public void stop(){
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }
}
