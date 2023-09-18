package com.ha1ey.CandleDragon;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class AppStart extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/Control.fxml")));
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("icon/icon.png")).toString()));
        primaryStage.setTitle("CandleDragon Vulnerability Exploitation Tools     V 2.1.4      Author：Ha1ey");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}