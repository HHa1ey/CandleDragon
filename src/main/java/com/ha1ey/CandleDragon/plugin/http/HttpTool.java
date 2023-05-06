package com.ha1ey.CandleDragon.plugin.http;


import com.ha1ey.CandleDragon.tools.Tools;
import com.ha1ey.CandleDragon.ui.MainController;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

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
            HttpURLConnection conn = getConn(url,connectTimeout);
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

    public static  Response sendFile(String url, HashMap<String,String> headers, Map<String,String> params, String fileKey, String filePath, String reFileName, int connectTimeout) {
        Response response = new Response(0, null, null, null);
        String boundary = "------WebKitFormBoundary" + Tools.randomStr(16);
        try {
            HttpURLConnection conn = getConn(url, connectTimeout);
            File file = new File(filePath);
            if (reFileName.equals("")) {
                reFileName = file.getName();
            }
            conn.setRequestMethod("POST");
            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("Content-Length", String.valueOf(file.length()));
            OutputStream outputStream = conn.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, MainController.charset), true);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"").append(entry.getKey()).append("\"").append("\r\n");
                writer.append("\r\n");
                writer.append(entry.getValue()).append("\r\n");
                writer.flush();
            }
            writer.append("--").append(boundary).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"").append(fileKey).append("\"; filename=\"").append(reFileName).append("\"").append("\r\n");
            writer.append("Content-Type: ").append(Files.probeContentType(file.toPath())).append("\r\n");
            writer.append("\r\n");
            writer.flush();
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            fileInputStream.close();
            writer.append("\r\n").flush();
            writer.append("--").append(boundary).append("--").append("\r\n");
            writer.close();
            response = getResponse(conn);
        }catch (Exception var9){
            response.setError(var9.getMessage());
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
