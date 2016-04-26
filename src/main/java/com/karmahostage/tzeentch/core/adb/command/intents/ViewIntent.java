package com.karmahostage.tzeentch.core.adb.command.intents;

import com.karmahostage.tzeentch.core.os.OsCommand;

public class ViewIntent {

    private String name = "android.intent.action.VIEW";
    private String fileType;
    private String fileLocation;

    public ViewIntent withFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public ViewIntent withFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
        return this;
    }

    public OsCommand toCommand() {
        return new OsCommand("adb", String.format("shell am start -a %s -t %s -d %s", name, fileType, toEncodedDestination()));
    }

    private String toEncodedDestination() {
        return "file:/" + fileLocation.replace("/", "//");
    }
}
