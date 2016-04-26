package com.karmahostage.tzeentch.core.adb;

import com.karmahostage.tzeentch.core.adb.model.AdbCommand;
import com.karmahostage.tzeentch.core.adb.model.AdbProcess;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(value = "com.karmahostage.tzeentch.environment", havingValue = "osx")
@Component
public class OsxAdbProcessBuilder implements AdbProcessBuilder {

    private static final String[] OS_LINUX_RUNTIME = {"/bin/bash", "-l", "-c"};


    @Override
    public AdbProcess buildProcess(AdbCommand adbCommand) {
        String[] concatenatedProcess = concat(OS_LINUX_RUNTIME, adbCommand.getCommand());
        ProcessBuilder pb = new ProcessBuilder(concatenatedProcess);
        pb.redirectErrorStream(true);
        return new AdbProcess(pb);
    }
}
