package com.karmahostage.tzeentch.core.adb.command;

import com.karmahostage.tzeentch.core.os.OsCommand;

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

    public OsCommand createCommand() {
        return new OsCommand("adb", "push " + source.getAbsolutePath() + " " + destination);
    }
}
