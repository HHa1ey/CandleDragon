package com.ha1ey.CandleDragon.entity;

import com.ha1ey.CandleDragon.plugin.ResultOutput;

import java.util.ArrayList;

public class ResultOutputPOJO implements ResultOutput {
    private final ArrayList<String> rawList = new ArrayList<>();
    private final ArrayList<String> debugList = new ArrayList<>();
    private final ArrayList<String> infoList = new ArrayList<>();
    private final ArrayList<String> successList = new ArrayList<>();
    private final ArrayList<String> failList = new ArrayList<>();
    private final ArrayList<String> warningList = new ArrayList<>();
    private final ArrayList<String> errorList = new ArrayList<>();


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


    @Override
    public void rawPrint(String rawmsg) {
        rawList.add(rawmsg);
    }

    @Override
    public void debugPrint(String debugmsg) {
        debugList.add("[&]"+debugmsg);
    }

    @Override
    public void infoPrint(String infomsg) {
        infoList.add("[*]"+infomsg);
    }

    @Override
    public void successPrint(String successmsg) {
        successList.add("[+]"+successmsg);
    }

    @Override
    public void failPrint(String failmsg) {
        failList.add("[-]"+failmsg);
    }

    @Override
    public void warningPrint(String warningmsg) {
        warningList.add("[!]"+warningmsg);
    }

    @Override
    public void errorPrint(String errormsg) {
        errorList.add("[x]"+errormsg);
    }

    @Override
    public void startPrint(String startmsg) {
    }

    @Override
    public void stopPrint(String stopmsg) {
    }
}
