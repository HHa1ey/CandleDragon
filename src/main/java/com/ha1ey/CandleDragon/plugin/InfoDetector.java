package com.ha1ey.CandleDragon.plugin;

import java.util.LinkedHashMap;
import java.util.Map;

public interface InfoDetector {
    public String getInfoDetectorTabTitle();

    public ArgsUsage getInfoDetectorCustomArgs();

    public LinkedHashMap<String, String> doDetect(TargetInfo targetinfo, Map<String, Object> customArgs, ResultOutput resultOutput) throws Throwable;

}
