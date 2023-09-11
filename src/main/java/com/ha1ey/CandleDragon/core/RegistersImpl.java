package com.ha1ey.CandleDragon.core;


import com.ha1ey.CandleDragon.common.CommonUtils;
import com.ha1ey.CandleDragon.plugin.IPlugin;
import com.ha1ey.CandleDragon.plugin.Register;
import com.ha1ey.CandleDragon.plugin.UtilPlugin;


public class RegistersImpl implements Register {

    @Override
    public void doRegister(IPlugin plugin) {
        PluginImpl pluginImpl = new PluginImpl();
        plugin.setPluginInfo(pluginImpl);
        CommonUtils.pluginList.add(pluginImpl);
    }

    @Override
    public void addUtil(UtilPlugin utilPlugin) {
        UtilsPluginImpl utilsImpl = new UtilsPluginImpl();
        utilPlugin.setUtilInfo(utilsImpl);
        CommonUtils.utilsList.add(utilsImpl);
    }



}
