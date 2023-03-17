package com.ha1ey.CandleDragon.entity;

import com.ha1ey.CandleDragon.plugin.ArgsInfo;
import com.ha1ey.CandleDragon.plugin.ArgsUsage;
import com.ha1ey.CandleDragon.plugin.HelpPluginInfo;
import com.ha1ey.CandleDragon.plugin.ScanResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class HelpPluginInfoPOJO implements HelpPluginInfo {

    @Override
    public ArgsInfo createArg() {
        return new ArgsInfoPOJO();
    }

    @Override
    public ArgsUsage createArgsUsage() {
        return new ArgsUsagePOJO();
    }

    @Override
    public ScanResult createScanResult() {
        return new ScanResultPOJO();
    }

    @Override
    public String getThrowableInfo(Throwable e) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        e.fillInStackTrace().printStackTrace(printWriter);
        return result.toString();
    }
}
