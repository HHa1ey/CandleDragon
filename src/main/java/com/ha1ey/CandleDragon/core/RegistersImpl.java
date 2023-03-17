package com.ha1ey.CandleDragon.core;

import com.ha1ey.CandleDragon.entity.InfoDetectorPOJO;
import com.ha1ey.CandleDragon.entity.VulPOJO;
import com.ha1ey.CandleDragon.plugin.InfoDetectorPlugin;
import com.ha1ey.CandleDragon.plugin.Registers;
import com.ha1ey.CandleDragon.plugin.VulPlugin;


public class RegistersImpl implements Registers {


    @Override
    public void registerInfoDetectorPlugin(InfoDetectorPlugin infoDetectorPlugin) {
        InfoDetectorPOJO infoDetectorPOJO = new InfoDetectorPOJO();
        infoDetectorPlugin.InfoDetectorPluginMain(infoDetectorPOJO);
        PluginPOJOList.infoDetectorPOJOList.add(infoDetectorPOJO);
    }

    @Override
    public void registerVulPlugin(VulPlugin vulPlugin) {
        VulPOJO vulPOJO = new VulPOJO();
        vulPlugin.VulPluginMain(vulPOJO);
        PluginPOJOList.vulPOJOList.add(vulPOJO);
    }
}
