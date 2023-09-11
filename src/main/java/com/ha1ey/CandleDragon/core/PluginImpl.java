package com.ha1ey.CandleDragon.core;


import com.ha1ey.CandleDragon.plugin.*;

import java.util.List;

public class PluginImpl implements PluginInfo {


    private String pluginName;
    private String pluginVersion;
    private String pluginAuthor;
    private String vulName;
    private String vulId;
    private String vulCategory;
    private String product;
    private String vulScope;
    private String description;
    private String vulDisclosureTime;

    private List<Exploit> exploits;

    private Poc poc;

    public String getPluginName() {
        return pluginName;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    public String getPluginAuthor() {
        return pluginAuthor;
    }

    public String getVulName() {
        return vulName;
    }

    public String getVulId() {
        return vulId;
    }

    public String getVulCategory() {
        return vulCategory;
    }

    public String getProduct() {
        return product;
    }

    public String getVulScope() {
        return vulScope;
    }

    public String getDescription() {
        return description;
    }

    public String getVulDisclosureTime() {
        return vulDisclosureTime;
    }

    public List<Exploit> getExploits() {
        return exploits;
    }

    public Poc getPoc() {
        return poc;
    }



    @Override
    public void setPluginName(String name) {
        this.pluginName = name;
    }

    @Override
    public void setPluginVersion(String version) {
        this.pluginVersion = version;
    }

    @Override
    public void setPluginAuthor(String author) {
        this.pluginAuthor = author;
    }

    @Override
    public void setVulName(String vulName) {
        this.vulName = vulName;
    }

    @Override
    public void setVulId(String vid) {
        this.vulId = vid;
    }


    @Override
    public void setVulCategory(String category) {
        this.vulCategory = category;
    }

    @Override
    public void setVulProduct(String product) {
        this.product = product;
    }

    @Override
    public void setVulScope(String scope) {
        this.vulScope = scope;
    }


    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setVulDisclosureTime(String disclosureTime) {
        this.vulDisclosureTime = disclosureTime;
    }

    @Override
    public void addExploit(List<Exploit> list) {
        this.exploits = list;
    }

    @Override
    public void addPoc(Poc poc) {
        this.poc = poc;
    }

}
