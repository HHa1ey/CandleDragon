package com.ha1ey.CandleDragon.common;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class ProxyHandler implements InvocationHandler {
    private Object obj;
    private String proxyAddress;
    private String proxyPort;
    private String proxyUsername;
    private String proxyPassword;
    private String proxyType;

    public ProxyHandler(Object obj, String proxyAddress, String proxyPort, String proxyUsername, String proxyPassword, String proxyType) {
        this.obj = obj;
        this.proxyAddress = proxyAddress;
        this.proxyPort = proxyPort;
        this.proxyUsername = proxyUsername;
        this.proxyPassword = proxyPassword;
        this.proxyType = proxyType;
    }

    public ProxyHandler(Object obj, String proxyAddress, String proxyPort, String proxyType) {
        this.obj = obj;
        this.proxyAddress = proxyAddress;
        this.proxyPort = proxyPort;
        this.proxyType = proxyType;
    }




    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (proxyType.equals("HTTP")){
            System.setProperty("http.proxyHost",this.proxyAddress);
            System.setProperty("http.proxyPort",this.proxyPort);
            System.setProperty("https.proxyHost",this.proxyAddress);
            System.setProperty("https.proxyPort",this.proxyPort);
        }
        if (proxyType.equals("SOCKS")){
            System.setProperty("socksProxyHost",this.proxyAddress);
            System.setProperty("socksProxyPort",this.proxyPort);
        }

        if (!StringUtils.isEmpty(this.proxyUsername)&&!StringUtils.isEmpty(this.proxyPassword)){
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(proxyUsername,proxyPassword.toCharArray());
                }
            });
        }
        Object result = method.invoke(obj,args);

        System.clearProperty("http.proxyHost");
        System.clearProperty("http.proxyPort");
        System.clearProperty("https.proxyHost");
        System.clearProperty("https.proxyPort");
        System.clearProperty("socksProxyHost");
        System.clearProperty("socksProxyPort");
        Authenticator.setDefault(null);

        return result;
    }


}
