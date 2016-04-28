package com.karmahostage.tzeentch.core.adb.command;

public class ShellCommand {

    public AmCommand am() {
        return new AmCommand();
    }

    public LogcatCommand logcat() {
        return new LogcatCommand();
    }

    public PsCommand ps() {
        return new PsCommand();
    }

    public InputCommand input() {
        return new InputCommand();
    }

    public CatCommand cat() {
        return new CatCommand();
    }
}
