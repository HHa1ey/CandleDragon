package com.ha1ey.CandleDragon.core;

/**
 * @author Ha1ey
 * @descrition
 * @date 2023-07-31 23:29
 **/
public class DNSLogResultEntity {
    private String time;
    private String reqip;
    private String domain;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReqip() {
        return reqip;
    }

    public void setReqip(String reqip) {
        this.reqip = reqip;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
