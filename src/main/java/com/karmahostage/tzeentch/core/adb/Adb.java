package com.karmahostage.tzeentch.core.adb;

import com.karmahostage.tzeentch.core.adb.command.PushCommand;
import com.karmahostage.tzeentch.core.adb.command.ShellCommand;

public class Adb {

    public static PushCommand push() {
        return new PushCommand();
    }

    public static ShellCommand shell() {
        return new ShellCommand();
    }

}
