package com.ha1ey.CandleDragon.plugin.http;


import com.ha1ey.CandleDragon.ui.MainController;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.HashMap;

public class HttpTool {

    //GET请求
    public static Response get(String url, HashMap<String, String> headers, int connectTimeout) {
        Response response = new Response(0, null, null, null);
        try {
            HttpURLConnection conn = getConn(url, connectTimeout);
            conn.setRequestMethod("GET");
            //创建迭代器
            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }
            response = getResponse(conn);
        } catch (SocketTimeoutException var6) {
            response.setError("连接超时！");
        } catch (IOException | KeyManagementException | NoSuchProviderException | NoSuchAlgorithmException var8) {
            response.setError(var8.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    //POST请求
    public static Response post(String url, HashMap<String, String> headers, String postStr, int connectTimeout) {
        Response response = new Response(0, null, null, null);
        try {
            HttpURLConnection conn = getConn(url, connectTimeout);
            conn.setRequestMethod("POST");

            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postStr.getBytes());
            outputStream.flush();
            outputStream.close();
            response = getResponse(conn);
        } catch (Exception var8) {
            response.setError(var8.getMessage());
        }
        return response;
    }

    private static Response getResponse(HttpURLConnection conn) {

        Response response = new Response(0, null, null, null);

        try {
            conn.connect();
            response.setCode(conn.getResponseCode());
            response.setHead(conn.getHeaderFields().toString());
            response.setText(streamToString(conn.getInputStream()));
        } catch (IOException var3) {
            response.setError(var3.toString());
        }
        return response;
    }

    private static HttpURLConnection getConn(String url, int connectTimeout) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException, ClassNotFoundException {
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        TrustManager[] trustManagers = new TrustManager[]{new Cert()};
        sslContext.init(null, trustManagers, new SecureRandom());
        HostnameVerifier ignoreHostnameVerifier = (s, sslSession) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        URL url_obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) url_obj.openConnection();

        //判断是否设置代理
        Proxy proxy = (Proxy) MainController.setProxy.get("proxy");
        if (proxy != null) {
            conn = (HttpURLConnection) url_obj.openConnection(proxy);
        }
        conn.setRequestProperty("User-Agent", UserAgent.getRandomUA());
        conn.setConnectTimeout(connectTimeout);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(false);
        return conn;
    }

    //返回包的流转换成字符串
    private static String streamToString(InputStream inputStream) throws UnsupportedEncodingException {
        String resultString;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, MainController.charset));
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            resultString = stringBuffer.toString();
        } catch (IOException var6) {
            resultString = var6.getMessage();
            var6.printStackTrace();
        }
        return resultString;
    }


}
