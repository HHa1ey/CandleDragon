package com.ha1ey.CandleDragon.tools;

import javafx.scene.control.Alert;
import javafx.stage.Window;

import java.util.Random;

public class Tools {
    //判断URL书写规范自动添加http
    public static String urlParse(String url){
        if(!url.contains("http")){
            url = "http://" +url;
        }
        if(url.endsWith("/")){
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

    public static String randomStr(int length){
        String str= "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random =new Random();
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<length;i++){
            int num = random.nextInt(62);
            sb.append(str.charAt(num));
        }
        return sb.toString();
    }

    public static void alert(String alert_info){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        Window window = alert.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest((e) -> {
            window.hide();
        });
        alert.setHeaderText(alert_info);
        alert.show();
    }
}
