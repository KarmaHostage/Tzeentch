package com.karmahostage.tzeentch.core.adb.command;

import java.io.File;

public class PushCommand {

    private File source;
    private String destination;

    public PushCommand withSource(File source) {
        this.source = source;
        return this;
    }

    public PushCommand withSource(String source) {
        this.source = new File(source);
        return this;
    }

    public PushCommand withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public AdbCommand createCommand() {
        return new AdbCommand("push " + source.getAbsolutePath() + " " + destination);
    }
}
