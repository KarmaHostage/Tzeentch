package com.karmahostage.tzeentch.core.adb.command;

public class ShellCommand {

    public AmCommand am() {
        return new AmCommand();
    }

    public LogcatCommand logcat() {
        return new LogcatCommand();
    }

}
