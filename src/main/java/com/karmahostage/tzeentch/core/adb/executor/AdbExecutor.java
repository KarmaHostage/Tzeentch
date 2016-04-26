package com.karmahostage.tzeentch.core.adb.executor;

import com.karmahostage.tzeentch.core.os.OsProcessBuilder;
import com.karmahostage.tzeentch.core.os.OsCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AdbExecutor {

    @Autowired
    private OsProcessBuilder adbProcessBuilder;

    public String execute(OsCommand osCommand) {
        return adbProcessBuilder.buildProcess(osCommand)
                .execute()
                .stream()
                .collect(Collectors.joining("\n"));
    }

}
