package com.karmahostage.tzeentch.core.adb.process;

import com.karmahostage.tzeentch.core.adb.command.AdbCommand;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(value = "com.karmahostage.tzeentch.environment", havingValue = "windows")
public class WindowsAdbProcessBuilder implements AdbProcessBuilder{

    private static final String[] WIN_RUNTIME = { "cmd.exe", "/C" };

    @Override
    public AdbProcess buildProcess(AdbCommand adbCommand) {
        throw new IllegalArgumentException("not yet implemented");
    }
}
