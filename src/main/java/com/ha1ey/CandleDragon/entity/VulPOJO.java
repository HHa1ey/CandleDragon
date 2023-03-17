package com.ha1ey.CandleDragon.entity;

import com.ha1ey.CandleDragon.plugin.*;

import java.util.LinkedList;
import java.util.List;

public class VulPOJO implements VulPluginInfo {

    private String name;
    private String version;
    private String path;
    private String author;
    private String vulName;
    private String vid;
    private double cvss;
    private String severity;
    private String category;
    private String product;
    private String scope;
    private String description;
    private String disclosureTime;
    private Poc poc;
    private List<Exploit> exploitList = new LinkedList<>();
    private HelpPluginInfo helpPluginInfo = new HelpPluginInfoPOJO();


    public String getVulPluginName() {
        return this.name;
    }

    public String getVulPluginVersion() {
        return this.version;
    }

    public String getVulPluginPath() {
        return this.path;
    }

    public String getVulPluginAuthor() {
        return this.author;
    }

    public String getVulName() {
        return this.vulName;
    }

    public String getVulId() {
        return this.vid;
    }

    public double getVulCVSS() {
        return this.cvss;
    }

    public String getVulSeverity() {
        return this.severity;
    }

    public String getVulCategory() {
        return this.category;
    }

    public String getVulProduct() {
        return this.product;
    }

    public String getVulScope() {
        return this.scope;
    }

    public String getVulDescription() {
        return this.description;
    }

    public String getVulDisclosureTime() {
        return this.disclosureTime;
    }

    public List<Exploit> getExploit() {
        return this.exploitList;
    }

    public Poc getPoc() {
        return this.poc;
    }

    @Override
    public void setVulPluginName(String name) {
        this.name = name;
    }

    @Override
    public void setVulPluginVersion(String version) {
        this.version = version;
    }

    @Override
    public void setVulPluginPath(String path) {
        this.path = path;
    }

    @Override
    public void setVulPluginAuthor(String author) {
        this.author = author;
    }

    @Override
    public void setVulName(String vulName) {
        this.vulName = vulName;
    }

    @Override
    public void setVulId(String vid) {
        this.vid = vid;
    }

    @Override
    public void setVulCVSS(double cvss) {
        this.cvss = cvss;
    }

    @Override
    public void setVulSeverity(String severity) {
        this.severity = severity;
    }

    @Override
    public void setVulCategory(String category) {
        this.category = category;
    }

    @Override
    public void setVulProduct(String product) {
        this.product = product;
    }

    @Override
    public void setVulScope(String scope) {
        this.scope = scope;
    }

    @Override
    public void setVulDescription(String description) {
        this.description = description;
    }

    @Override
    public void setVulDisclosureTime(String disclosureTime) {
        this.disclosureTime = disclosureTime;
    }

    @Override
    public void registerPoc(Poc poc) {
        this.poc = poc;
    }

    @Override
    public void registerExploit(List<Exploit> exploitList) {
        this.exploitList = exploitList;
    }


    @Override
    public HelpPluginInfo getHelpPluginInfo() {
        return this.helpPluginInfo;
    }

}
