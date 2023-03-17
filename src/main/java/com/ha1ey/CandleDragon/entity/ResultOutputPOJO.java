package com.ha1ey.CandleDragon.entity;

import com.ha1ey.CandleDragon.plugin.ResultOutput;

import java.util.ArrayList;

public class ResultOutputPOJO implements ResultOutput {
    private ArrayList<String> rawList = new ArrayList<>();
    private ArrayList<String> debugList = new ArrayList<>();
    private ArrayList<String> infoList = new ArrayList<>();
    private ArrayList<String> successList = new ArrayList<>();
    private ArrayList<String> failList = new ArrayList<>();
    private ArrayList<String> warningList = new ArrayList<>();
    private ArrayList<String> errorList = new ArrayList<>();
    private ArrayList<String> startList = new ArrayList<>();
    private ArrayList<String> stopList = new ArrayList<>();


    public ArrayList<String> getRawList() {
        return rawList;
    }

    public ArrayList<String> getDebugList() {
        return debugList;
    }

    public ArrayList<String> getInfoList() {
        return infoList;
    }

    public ArrayList<String> getSuccessList() {
        return successList;
    }

    public ArrayList<String> getFailList() {
        return failList;
    }

    public ArrayList<String> getWarningList() {
        return warningList;
    }

    public ArrayList<String> getErrorList() {
        return errorList;
    }

    public ArrayList<String> getStartList() {
        return startList;
    }

    public ArrayList<String> getStopList() {
        return stopList;
    }

    @Override
    public void rawPrint(String rawmsg) {
        rawList.add(rawmsg);
    }

    @Override
    public void debugPrint(String debugmsg) {
        debugList.add(debugmsg);
    }

    @Override
    public void infoPrint(String infomsg) {
        infoList.add(infomsg);
    }

    @Override
    public void successPrint(String successmsg) {
        successList.add(successmsg);
    }

    @Override
    public void failPrint(String failmsg) {
        failList.add(failmsg);
    }

    @Override
    public void warningPrint(String warningmsg) {
        warningList.add(warningmsg);
    }

    @Override
    public void errorPrint(String errormsg) {
        errorList.add(errormsg);
    }

    @Override
    public void startPrint(String startmsg) {
        startList.add(startmsg);
    }

    @Override
    public void stopPrint(String stopmsg) {
        stopList.add(stopmsg);
    }
}
