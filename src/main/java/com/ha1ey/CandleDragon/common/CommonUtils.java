package com.ha1ey.CandleDragon.common;

import com.ha1ey.CandleDragon.core.PluginImpl;
import com.ha1ey.CandleDragon.core.UtilsPluginImpl;
import javafx.scene.control.Alert;
import javafx.stage.Window;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtils {

    public static List<PluginImpl> pluginList = new LinkedList<>();
    public static List<UtilsPluginImpl> utilsList = new LinkedList<>();
    private static final String CODES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

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

    public static String getDate(){
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(now);
    }


    public static String strToUnicode(String string){
        StringBuffer stringBuffer = new StringBuffer();
        try{
            CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
            CharBuffer charBuffer  =CharBuffer.wrap(string);
            ByteBuffer byteBuffer = encoder.encode(charBuffer);
            while (byteBuffer.hasRemaining()){
                int code = byteBuffer.get() & 0xFF;
                stringBuffer.append(String.format("\\u%04x",code));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }


    public static String base64Encode(byte[] in) {
        StringBuilder out = new StringBuilder((in.length * 4) / 3);
        int b;
        for (int i = 0; i < in.length; i += 3) {
            b = (in[i] & 0xFC) >> 2;
            out.append(CODES.charAt(b));
            b = (in[i] & 0x03) << 4;
            if (i + 1 < in.length) {
                b |= (in[i + 1] & 0xF0) >> 4;
                out.append(CODES.charAt(b));
                b = (in[i + 1] & 0x0F) << 2;
                if (i + 2 < in.length) {
                    b |= (in[i + 2] & 0xC0) >> 6;
                    out.append(CODES.charAt(b));
                    b = in[i + 2] & 0x3F;
                    out.append(CODES.charAt(b));
                } else {
                    out.append(CODES.charAt(b));
                    out.append('=');
                }
            } else {
                out.append(CODES.charAt(b));
                out.append("==");
            }
        }
        return out.toString();
    }


    //解码
    public static byte[] base64Decode(String input) {
        return Base64.getDecoder().decode(input.getBytes());
    }

}
