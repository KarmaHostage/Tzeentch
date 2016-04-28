package com.karmahostage.tzeentch.core.adb.command;

import com.karmahostage.tzeentch.core.os.OsCommand;

public class CatCommand {

    public OsCommand doCat(String toCat) {
        return new OsCommand("adb", "shell cat " + toCat);
    }

}
