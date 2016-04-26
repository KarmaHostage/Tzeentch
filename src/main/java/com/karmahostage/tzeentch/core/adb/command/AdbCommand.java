package com.karmahostage.tzeentch.core.adb.command;

public class AdbCommand {
    private String command;

    public AdbCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return getProgram() + command;
    }

    private String getProgram() {
        return "adb ";
    }
}
