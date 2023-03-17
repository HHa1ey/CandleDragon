package com.ha1ey.CandleDragon.entity;

import com.ha1ey.CandleDragon.plugin.ArgsInfo;

import java.util.LinkedList;
import java.util.List;

public class ArgsInfoPOJO implements ArgsInfo {
    public final static int ARG_TYPE_STRING = 0;
    public final int ARG_TYPE_INT = 1;
    public final int ARG_TYPE_DOUBLE = 2;
    public final int ARG_TYPE_PORT = 3;
    public final int ARG_TYPE_IP = 4;
    public final int ARG_TYPE_HTTP_URL = 5;
    public final int ARG_TYPE_ADDRESS = 6;
    public final int ARG_TYPE_ENUM = 7;
    public final int ARG_TYPE_BOOLEAN = 8;
    public final int ARG_TYPE_DICT = 9;

    private String name;
    private int type;
    private String defaultValue;
    private List<String> enumValue = new LinkedList<>();
    private boolean is;
    private String desc;

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public List<String> getEnumValue() {
        return enumValue;
    }

    public boolean isIs() {
        return is;
    }

    public String getDesc() {
        return desc;
    }


    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public void setEnumValue(List<String> enumValue) {
        this.enumValue = enumValue;
    }

    @Override
    public void setRequired(boolean is) {
        this.is = is;
    }

    @Override
    public void setDescription(String desc) {
        this.desc = desc;
    }
}
