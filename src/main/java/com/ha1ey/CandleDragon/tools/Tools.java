package com.ha1ey.CandleDragon.tools;

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
}
