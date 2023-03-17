package com.ha1ey.CandleDragon.ui;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class GlobalMenu extends ContextMenu {
    private static GlobalMenu INSTANCE = null;

    private GlobalMenu() {
        MenuItem sendToPOC = new MenuItem("发送到POC");
        MenuItem sendToEXP = new MenuItem("发送到EXP");
        getItems().add(sendToPOC);
        getItems().add(sendToEXP);
    }

    public static GlobalMenu getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlobalMenu();
        }
        return INSTANCE;
    }
}
