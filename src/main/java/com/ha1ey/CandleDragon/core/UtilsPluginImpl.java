package com.ha1ey.CandleDragon.core;


import com.ha1ey.CandleDragon.plugin.UtilInfo;
import javafx.scene.Node;

/**
 * @Author Ha1ey
 * @Date 2023/9/8 17:34
 * @PackageName:com.ha1ey.CandleDragon.core
 * @ClassName: UtilsPluginImpl
 * @Description: TODO
 */
public class UtilsPluginImpl implements UtilInfo {

    private Node view;
    private String utilName;
    private String description;

    public String getUtilName() {
        return utilName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void setView(Node node) {
        this.view = node;
    }

    @Override
    public void setUtilName(String name) {
        this.utilName = name;
    }

    @Override
    public void setDescription(String name) {
        this.description = name;
    }

    public Node getView(){
        return this.view;
    }
}
