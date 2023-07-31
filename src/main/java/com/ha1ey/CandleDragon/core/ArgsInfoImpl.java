package com.ha1ey.CandleDragon.core;

import com.ha1ey.CandleDragon.plugin.ArgsInfo;

public class ArgsInfoImpl implements ArgsInfo {
    private String argsName;
    private String defaultValue;
    private String description;
    public String getArgsName() {
        return argsName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public void setArgsName(String str) {
        this.argsName = str;
    }

    @Override
    public void setDefaultValue(String str) {
        this.defaultValue = str;
    }

    @Override
    public void setDescription(String str) {
        this.description = str;
    }
}
