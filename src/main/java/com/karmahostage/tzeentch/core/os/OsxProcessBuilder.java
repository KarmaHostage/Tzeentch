package com.karmahostage.tzeentch.core.os;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(value = "com.karmahostage.tzeentch.environment", havingValue = "osx")
@Component
public class OsxProcessBuilder implements OsProcessBuilder {

    private static final String[] OS_LINUX_RUNTIME = {"/bin/bash", "-l", "-c"};


    @Override
    public OsProcess buildProcess(OsCommand osCommand) {
        String[] concatenatedProcess = concat(OS_LINUX_RUNTIME, osCommand.getCommand());
        ProcessBuilder pb = new ProcessBuilder(concatenatedProcess);
        pb.redirectErrorStream(true);
        return new OsProcess(pb);
    }
}
