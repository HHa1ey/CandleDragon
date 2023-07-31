package com.ha1ey.CandleDragon.core;

import com.ha1ey.CandleDragon.plugin.TargetInfo;

public class TargetInfoImpl implements TargetInfo {
    private String address;
    private String userAgent;
    private int timeout;
    private String charset;


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
}
