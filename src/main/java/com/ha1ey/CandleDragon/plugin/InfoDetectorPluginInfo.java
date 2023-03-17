package com.ha1ey.CandleDragon.plugin;

import java.util.List;

public interface InfoDetectorPluginInfo {
    public void setInfoDetectorPluginName(String name);

    public void setInfoDetectorPluginVersion(String version);

    public void setInfoDetectorPluginPath(String path);

    public void setInfoDetectorPluginAuthor(String author);

    public void setInfoDetectorPluginDescription(String description);

    public void registerInfoDetector(List<InfoDetector> infoDetectorList);

    public HelpPluginInfo getHelpPluginInfo();
}
