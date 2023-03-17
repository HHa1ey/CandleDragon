package com.ha1ey.CandleDragon.plugin;

public interface ScanResult {
    public void setTarget(String target);

    public void setVul(boolean isvul);

    public void setMsg(String msg);

    public void setTime(String time);

    public boolean isVul();
}
