package com.karmahostage.tzeentch.core.adb.command;

import com.karmahostage.tzeentch.core.os.OsCommand;

public class InputKeyEventCommand {

    public OsCommand keyEvent(KeyEvent keyEvent) {
        return new OsCommand("adb", String.format("shell input keyevent %s", keyEvent.getEventString()));
    }

}
