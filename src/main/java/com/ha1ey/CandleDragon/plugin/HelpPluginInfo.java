package com.ha1ey.CandleDragon.plugin;

public interface HelpPluginInfo {
    public ArgsInfo createArg();

    public ArgsUsage createArgsUsage();

    public ScanResult createScanResult();

    public String getThrowableInfo(Throwable e);

}
