package com.ha1ey.CandleDragon.controller;

import com.ha1ey.CandleDragon.common.CommonUtils;
import com.ha1ey.CandleDragon.common.ComponentUtil;
import com.ha1ey.CandleDragon.core.UtilsPluginImpl;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.awt.*;
import java.net.URI;
import java.util.HashMap;


public class Controller {
    @FXML
    private HBox functionsHBox;
    @FXML
    private Label proxyStatusLabel;
    @FXML
    private FontAwesomeIconView utils;
    @FXML
    private FontAwesomeIconView setting;
    @FXML
    private FontAwesomeIconView debug;
    @FXML
    private FontAwesomeIconView github;
    @FXML
    private FontAwesomeIconView reload;
    @FXML
    private FontAwesomeIconView vul;

    public static HashMap<String, Parent> components = new HashMap<>();
    public static HashMap<String, Object> controllers = new HashMap<>();


    @FXML
    private void initialize() {
        ComponentUtil.addComponent("home", "fxml/Home.fxml", components, controllers);
        ComponentUtil.addComponent("setting", "fxml/Setting.fxml", components, controllers);
        ComponentUtil.addComponent("debug", "fxml/DeBug.fxml", components, controllers);
        controllers.put("control", this);
        proxyStatusLabel.setText("ProxyStatus: \tClose");

        initTips();
        runHome();
    }

    private void initTips(){
        Tooltip reloadtip = new Tooltip("reload plugins");
        reload.setOnMouseEntered(event -> {
            reloadtip.show(reload, event.getScreenX() + 10, event.getScreenY() + 10);
        });

        reload.setOnMouseExited(event -> {
            reloadtip.hide();
        });


        Tooltip vultip = new Tooltip("Vul module");
        vul.setOnMouseEntered(event -> {
            vultip.show(vul, event.getScreenX() + 10, event.getScreenY() + 10);
        });

        vul.setOnMouseExited(event -> {
            vultip.hide();
        });


        Tooltip utilstip = new Tooltip("Utils module");
        utils.setOnMouseEntered(event -> {
            utilstip.show(utils, event.getScreenX() + 10, event.getScreenY() + 10);
        });

        utils.setOnMouseExited(event -> {
            utilstip.hide();
        });


        Tooltip settingtip = new Tooltip("setting module");
        setting.setOnMouseEntered(event -> {
            settingtip.show(setting, event.getScreenX() + 10, event.getScreenY() + 10);
        });

        setting.setOnMouseExited(event -> {
            settingtip.hide();
        });


        Tooltip debugtip = new Tooltip("debug module");
        debug.setOnMouseEntered(event -> {
            debugtip.show(debug, event.getScreenX() + 10, event.getScreenY() + 10);
        });

        debug.setOnMouseExited(event -> {
            debugtip.hide();
        });

        Tooltip githubtip = new Tooltip("github module");
        github.setOnMouseEntered(event -> {
            githubtip.show(github, event.getScreenX() + 10, event.getScreenY() + 10);
        });

        github.setOnMouseExited(event -> {
            githubtip.hide();
        });
    }

    public void setProxyStatusLabel(String proxyIP, String proxyPort, String proxyType, Boolean isProxy) {
        if (isProxy && !proxyIP.isEmpty() && !proxyPort.isEmpty()) {
            proxyStatusLabel.setText("ProxyStatus: \t" + proxyType + "\t" + proxyIP + ":" + proxyPort + "\tOpen");
        } else {
            proxyStatusLabel.setText("ProxyStatus: \tClose");
        }

    }


    @FXML
    private void runHome() {
        HBox mainHBox = (HBox) components.get("home");
        if (functionsHBox.getChildren().size() > 1) {
            functionsHBox.getChildren().remove(1);
        }
        functionsHBox.getChildren().add(1, mainHBox);
        functionsHBox.setHgrow(mainHBox, Priority.ALWAYS);

    }


    @FXML
    private void runSetting() {
        SplitPane settingSplitPane = (SplitPane) components.get("setting");
        if (functionsHBox.getChildren().size() > 1) {
            functionsHBox.getChildren().remove(1);
        }
        functionsHBox.getChildren().add(1, settingSplitPane);
        functionsHBox.setHgrow(settingSplitPane, Priority.ALWAYS);
    }


    @FXML
    private void runUtils() {
        JFXTabPane utilsTabPane = new JFXTabPane();
        if (CommonUtils.utilsList != null) {
            for (UtilsPluginImpl util : CommonUtils.utilsList) {
                Node node = util.getView();
                Tab tab = new Tab();
                SplitPane splitPane = new SplitPane();
                splitPane.setOrientation(Orientation.VERTICAL);
                TitledPane tips = new TitledPane();
                tips.setText("Tips");
                JFXTextArea textArea = new JFXTextArea();
                textArea.setText(util.getDescription());
                tips.setContent(textArea);
                splitPane.getItems().add(0, tips);
                splitPane.getItems().add(node);
                if (!util.getUtilName().isEmpty()) {
                    tab.setText(util.getUtilName());
                } else {
                    tab.setText("Default UtilName");
                }

                tab.setContent(splitPane);
                utilsTabPane.getTabs().add(tab);
            }
        }


        if (functionsHBox.getChildren().size() > 1) {
            functionsHBox.getChildren().remove(1);
        }
        functionsHBox.getChildren().add(1, utilsTabPane);
        functionsHBox.setHgrow(utilsTabPane, Priority.ALWAYS);
    }


    @FXML
    private void runBug() {
        JFXTabPane debugTabPane = (JFXTabPane) components.get("debug");
        if (functionsHBox.getChildren().size() > 1) {
            functionsHBox.getChildren().remove(1);
        }
        functionsHBox.getChildren().add(1, debugTabPane);
        functionsHBox.setHgrow(debugTabPane, Priority.ALWAYS);
    }

    @FXML
    private void reloadPlugin(){
        HomeController homeController = (HomeController) controllers.get("home");
        homeController.initPlugin();

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
