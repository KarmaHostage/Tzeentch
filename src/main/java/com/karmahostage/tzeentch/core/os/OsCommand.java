package com.karmahostage.tzeentch.core.os;

public class OsCommand {
    private String programName;
    private String command;

    public OsCommand(String programName, String command) {
        this.programName = programName;
        this.command = command;
    }

    public String getCommand() {
        return getProgram() + command;
    }

    private String getProgram() {
        return programName + " ";
    }
}
