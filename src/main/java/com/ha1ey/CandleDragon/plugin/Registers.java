package com.ha1ey.CandleDragon.plugin;


public interface Registers {
    //注册信息探测插件
    public void registerInfoDetectorPlugin(InfoDetectorPlugin plugin);

    //注册漏洞插件
    public void registerVulPlugin(VulPlugin plugin);
}
