package com.ha1ey.CandleDragon.common;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.util.Map;

public class ComponentUtil {

    public static void addComponent(String componentName, String fxmlPath,Map<String,Parent> components,Map<String,Object> controllers){
        try {
            FXMLLoader loader = new FXMLLoader(ComponentUtil.class.getClassLoader().getResource(fxmlPath));
            Parent component = loader.load();
            Object obj = loader.getController();
            components.put(componentName,component);
            controllers.put(componentName,obj);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Parent loadComponent(String componentName, String fxmlPath,Map<String,Parent> components){
        Parent component = null;
        try {
            FXMLLoader loader = new FXMLLoader(ComponentUtil.class.getClassLoader().getResource(fxmlPath));
             component = loader.load();
            components.put(componentName,component);
        }catch (Exception e){
            e.printStackTrace();
        }
        return component;
    }

}
