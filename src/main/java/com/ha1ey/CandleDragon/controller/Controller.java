package com.ha1ey.CandleDragon.controller;

import com.ha1ey.CandleDragon.common.ComponentUtil;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.net.URI;
import java.util.HashMap;


public class Controller {
    @FXML
    private VBox rootVBox;
    @FXML
    private Label proxyStatusLabel;


    public static HashMap<String, Parent> components = new HashMap<>();
    public static HashMap<String, Object> controllers = new HashMap<>();


    @FXML
    private void initialize() {
        ComponentUtil.addComponent("home", "fxml/Home.fxml", components, controllers);
        ComponentUtil.addComponent("setting", "fxml/Setting.fxml", components, controllers);
        ComponentUtil.addComponent("utils","fxml/Utils.fxml",components,controllers);
        ComponentUtil.addComponent("debug","fxml/DeBug.fxml",components,controllers);
        controllers.put("control",this);
        proxyStatusLabel.setText("ProxyStatus: \tClose");
        runHome();
    }



    public void setProxyStatusLabel(String proxyIP,String proxyPort,String proxyType,Boolean isProxy){
        if (isProxy && !proxyIP.isEmpty() && !proxyPort.isEmpty() ){
            proxyStatusLabel.setText("ProxyStatus: \t"+proxyType+"\t"+proxyIP+":"+proxyPort+"\tOpen");
        }else {
            proxyStatusLabel.setText("ProxyStatus: \tClose");
        }

    }




    @FXML
    private void runHome() {
        HBox mainHBox = (HBox) components.get("home");
        HBox functionsHBox = (HBox) rootVBox.lookup("#functionsHBox");
        if (functionsHBox.getChildren().size() > 1) {
            functionsHBox.getChildren().remove(1);
        }
        functionsHBox.getChildren().add(1, mainHBox);

    }


    @FXML
    private void runSetting() {
        SplitPane settingSplitPane = (SplitPane) components.get("setting");
        HBox functionsHBox = (HBox) rootVBox.lookup("#functionsHBox");
        if (functionsHBox.getChildren().size() > 1) {
            functionsHBox.getChildren().remove(1);
        }
        functionsHBox.getChildren().add(1, settingSplitPane);
    }


    @FXML
    private void runUtils(){
        JFXTabPane utilsTabPane = (JFXTabPane) components.get("utils");
        HBox functionsHBox = (HBox) rootVBox.lookup("#functionsHBox");
        if (functionsHBox.getChildren().size() > 1) {
            functionsHBox.getChildren().remove(1);
        }
        functionsHBox.getChildren().add(1, utilsTabPane);
    }


    @FXML
    private void runBug(){
        JFXTabPane debugTabPane = (JFXTabPane) components.get("debug");
        HBox functionsHBox = (HBox) rootVBox.lookup("#functionsHBox");
        if (functionsHBox.getChildren().size() > 1) {
            functionsHBox.getChildren().remove(1);
        }
        functionsHBox.getChildren().add(1, debugTabPane);
    }


    @FXML
    private void runGithub() {
        URI uri = URI.create("https://github.com/HHa1ey/CandleDragon");
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
