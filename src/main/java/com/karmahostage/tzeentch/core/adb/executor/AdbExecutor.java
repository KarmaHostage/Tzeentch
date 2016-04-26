package com.karmahostage.tzeentch.core.adb.executor;

import com.karmahostage.tzeentch.core.adb.process.AdbProcessBuilder;
import com.karmahostage.tzeentch.core.adb.command.AdbCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AdbExecutor {

    @Autowired
    private AdbProcessBuilder adbProcessBuilder;

    public String execute(AdbCommand adbCommand) {
        return adbProcessBuilder.buildProcess(adbCommand)
                .execute()
                .stream()
                .collect(Collectors.joining("\n"));
    }

}
