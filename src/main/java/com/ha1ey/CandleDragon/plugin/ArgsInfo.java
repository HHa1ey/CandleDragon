package com.ha1ey.CandleDragon.plugin;

import java.util.List;

public interface ArgsInfo {
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

    public void setName(String name);

    public void setType(int type);

    public void setDefaultValue(String defaultValue);

    public void setEnumValue(List<String> enumValue);

    public void setRequired(boolean is);

    public void setDescription(String desc);
}
