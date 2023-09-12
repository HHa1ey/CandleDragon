package com.ha1ey.CandleDragon.core;

import com.ha1ey.CandleDragon.common.CommonUtils;
import com.ha1ey.CandleDragon.plugin.Result;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ResultImpl implements Result {
    private List<String> info = new ArrayList<>();
    private List<String> error = new ArrayList<>();
    private List<String> raw = new ArrayList<>();
    private List<String> fail = new ArrayList<>();
    private List<String> success = new ArrayList<>();


    private String pluginName;

    private String pocTarget;
    private Boolean pocVul;

    private String pocMsg;
    private String pocTime;


    public ResultImpl() {

    }

    public List<String> getInfo() {
        return this.info;
    }

    public List<String> getError() {
        return this.error;
    }

    public List<String> getRaw() {
        return this.raw;
    }

    public List<String> getFail() {
        return this.fail;
    }

    public List<String> getSuccess() {
        return this.success;
    }




    public String getPocTarget() {
        return pocTarget;
    }

    public Boolean getPocVul() {
        return pocVul;
    }

    public String getPocMsg() {
        return pocMsg;
    }

    public String getPocTime() {
        return this.pocTime;
    }
    public String getPluginName(){
        return this.pluginName;
    }
    public void setPluginName(String str){
        this.pluginName = str;
    }
    public void setPocTime(String pocTime){
        this.pocTime = pocTime;
    }
    public void setPocTarget(String target) {
        this.pocTarget = target;
    }


    @Override
    public void printInfo(String str) {
        this.info.add("【!】"+str);
    }

    @Override
    public void printError(Throwable throwable) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        throwable.fillInStackTrace().printStackTrace(printWriter);
        this.error.add("【x】"+ result.toString());
    }

    @Override
    public void printRaw(String str) {
        this.raw.add("【*】"+str);
    }

    @Override
    public void printFail(String str) {
        this.fail.add("【-】"+str);
    }

    @Override
    public void printSuccess(String str) {
        this.fail.add("【✓】"+str);
    }



    @Override
    public void setPocVul(boolean isvul) {
        this.pocVul = isvul;
    }

    @Override
    public void setPocMsg(String msg) {
        this.pocMsg = msg;
    }


    @Override
    public boolean isPocVul() {
        return this.pocVul;
    }


}
