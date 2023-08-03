package com.ha1ey.CandleDragon.core;

import com.ha1ey.CandleDragon.plugin.ArgsInfo;
import com.ha1ey.CandleDragon.plugin.HelpPlugin;

public class HelpPluginImpl implements HelpPlugin {
    @Override
    public ArgsInfo createArgs() {
        return new ArgsInfoImpl();
    }



}
