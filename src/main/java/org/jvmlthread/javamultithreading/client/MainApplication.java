package org.jvmlthread.javamultithreading.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainApplication extends Application {
    private static final List<Runnable> stopHandlers = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/org/jvmlthread/javamultithreading/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Shooty-Shoot Game");
        scene.getStylesheets().add(getClass().getResource("/org/jvmlthread/javamultithreading/dark-theme.css").toExternalForm());
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {

        for(Runnable r : stopHandlers) {
            r.run();
        }
    }

    public static void addStopHandler(Runnable r) {
        stopHandlers.add(r);
    }

    public static void removeStopHandler(Runnable r) {
        stopHandlers.remove(r);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    private static Stage mainStage;
    public static void main(String[] args) {
        launch();
    }
}
