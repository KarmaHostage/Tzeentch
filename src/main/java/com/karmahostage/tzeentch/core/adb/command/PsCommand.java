package com.karmahostage.tzeentch.core.adb.command;

import com.karmahostage.tzeentch.core.os.OsCommand;

public class PsCommand {

    public OsCommand andLookForProcessName(String processName) {
        return new OsCommand("adb", String.format("shell ps | grep %s", processName));
    }

}
