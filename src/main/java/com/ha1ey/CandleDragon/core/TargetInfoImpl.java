package com.ha1ey.CandleDragon.core;

import com.ha1ey.CandleDragon.plugin.TargetInfo;

public class TargetInfoImpl implements TargetInfo {
    private String address;
    private String userAgent;
    private int timeout;
    private String charset;
    private String dnslog;
    private String cookie;

    public void setDnslog(String dnslog){
        this.dnslog = dnslog;
    }
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setCharset(String charset){
        this.charset = charset;
    }

    public void setCookie(String cookie){
        this.cookie = cookie;
    }
    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public String getUserAgent() {
        return this.userAgent;
    }

    @Override
    public int getTimeout() {
        return this.timeout;
    }

    @Override
    public String getCharset() {
        return this.charset;
    }

    @Override
    public String getDNSLog() {
        return this.dnslog;
    }

    @Override
    public String getCookie() {
        return this.cookie;
    }
}
