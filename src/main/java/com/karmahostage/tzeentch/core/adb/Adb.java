package com.karmahostage.tzeentch.core.adb;

import com.karmahostage.tzeentch.core.adb.command.PushCommand;

public class Adb {

    public static PushCommand push() {
        return new PushCommand();
    }

}
