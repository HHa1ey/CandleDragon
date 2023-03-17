package com.ha1ey.CandleDragon.entity;

import com.ha1ey.CandleDragon.plugin.HelpPluginInfo;
import com.ha1ey.CandleDragon.plugin.InfoDetector;
import com.ha1ey.CandleDragon.plugin.InfoDetectorPluginInfo;

import java.util.LinkedList;
import java.util.List;

public class InfoDetectorPOJO implements InfoDetectorPluginInfo {
    private String name;
    private String version;
    private String path;
    private String author;
    private String description;
    private List<InfoDetector> infoDetectorList = new LinkedList<>();
    private final HelpPluginInfo helpPluginInfo = new HelpPluginInfoPOJO();


    public String getInfoDetectorPluginName() {
        return this.name;
    }

    public String getInfoDetectorPluginVersion() {
        return this.version;
    }

    public String getInfoDetectorPluginPath() {
        return this.path;
    }

    public String getInfoDetectorPluginAuthor() {
        return this.author;
    }

    public String getInfoDetectorPluginDescription() {
        return this.description;
    }

    public List<InfoDetector> getInfoDetector() {
        return this.infoDetectorList;
    }

    @Override
    public void setInfoDetectorPluginName(String name) {
        this.name = name;
    }

    @Override
    public void setInfoDetectorPluginVersion(String version) {
        this.version = version;
    }

    @Override
    public void setInfoDetectorPluginPath(String path) {
        this.path = path;
    }

    @Override
    public void setInfoDetectorPluginAuthor(String author) {
        this.author = author;
    }

    @Override
    public void setInfoDetectorPluginDescription(String description) {
        this.description = description;
    }

    @Override
    public void registerInfoDetector(List<InfoDetector> infoDetectorList) {
        this.infoDetectorList = infoDetectorList;
    }

    @Override
    public HelpPluginInfo getHelpPluginInfo() {
        return this.helpPluginInfo;
    }
}
