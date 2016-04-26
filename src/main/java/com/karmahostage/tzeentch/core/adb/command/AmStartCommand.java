package com.karmahostage.tzeentch.core.adb.command;

import com.karmahostage.tzeentch.core.adb.command.intents.ViewIntent;

public class AmStartCommand {

    public ViewIntent viewIntent() {
        return new ViewIntent();
    }
}
