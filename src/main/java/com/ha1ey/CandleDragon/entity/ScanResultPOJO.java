package com.ha1ey.CandleDragon.entity;

import com.ha1ey.CandleDragon.plugin.ScanResult;

public class ScanResultPOJO implements ScanResult {
    private String target;
    private boolean isVul;
    private String msg;
    private String time;

    public String getTarget() {
        return this.target;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getTime() {
        return this.time;
    }


    @Override
    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public void setVul(boolean isVul) {
        this.isVul = isVul;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean isVul() {
        return this.isVul;
    }
}
