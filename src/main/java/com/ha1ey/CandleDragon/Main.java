package com.ha1ey.CandleDragon;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/Main.fxml")));
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("icon/icon.png")).toString()));
        primaryStage.setTitle("CandleDragon 烛龙  【插件化漏洞利用工具V1.3】      Author：Ha1ey");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    public static void main(String[] args) {
        launch(args);
    }
}