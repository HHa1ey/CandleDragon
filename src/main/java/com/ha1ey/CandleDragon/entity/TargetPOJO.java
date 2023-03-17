package com.ha1ey.CandleDragon.entity;

import com.ha1ey.CandleDragon.plugin.TargetInfo;

public class TargetPOJO implements TargetInfo {
    private String address;
    private String rootAddress;
    private String protocol;
    private String host;
    private String port;
    private String path;


    public void setAddress(String address) {
        this.address = address;
    }

    public void setRootAddress(String rootAddress) {
        this.rootAddress = rootAddress;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public String getRootAddress() {
        return this.rootAddress;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public String getPort() {
        return this.port;
    }

    @Override
    public String getPath() {
        return this.path;
    }
}
