package com.ha1ey.CandleDragon.entity;

import com.ha1ey.CandleDragon.plugin.ArgsInfo;
import com.ha1ey.CandleDragon.plugin.ArgsUsage;

import java.util.LinkedList;
import java.util.List;

public class ArgsUsagePOJO implements ArgsUsage {

    private List<ArgsInfo> argsInfoList = new LinkedList<>();
    private String usage;

    public List<ArgsInfo> getArgsInfoList() {
        return this.argsInfoList;
    }

    public String getUsage() {
        return this.usage;
    }

    @Override
    public void setArgsInfoList(List<ArgsInfo> argsInfoList) {
        this.argsInfoList = argsInfoList;
    }

    @Override
    public void setUsage(String usage) {
        this.usage = usage;
    }
}
