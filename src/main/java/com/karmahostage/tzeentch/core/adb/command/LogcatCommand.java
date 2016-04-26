package com.karmahostage.tzeentch.core.adb.command;

import com.karmahostage.tzeentch.core.os.OsCommand;

public class LogcatCommand {

    public OsCommand clear() {
        return new OsCommand("adb", "shell logcat -c");
    }

    public OsCommand dump() {
        return new OsCommand("adb", "shell logcat -d");
    }

}
