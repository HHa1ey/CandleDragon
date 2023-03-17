package com.ha1ey.CandleDragon.plugin;

public interface ResultOutput {
    public void rawPrint(String msg);

    public void debugPrint(String msg);

    public void infoPrint(String msg);

    public void successPrint(String msg);

    public void failPrint(String msg);

    public void warningPrint(String msg);

    public void errorPrint(String msg);

    public void startPrint(String msg);

    public void stopPrint(String msg);
}
