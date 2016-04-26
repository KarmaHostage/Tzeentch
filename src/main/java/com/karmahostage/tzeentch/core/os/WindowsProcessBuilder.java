package com.karmahostage.tzeentch.core.os;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(value = "com.karmahostage.tzeentch.environment", havingValue = "windows")
public class WindowsProcessBuilder implements OsProcessBuilder {

    private static final String[] WIN_RUNTIME = { "cmd.exe", "/C" };

    @Override
    public OsProcess buildProcess(OsCommand osCommand) {
        throw new IllegalArgumentException("not yet implemented");
    }
}
