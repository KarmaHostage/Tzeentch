package com.karmahostage.tzeentch.core.adb.executor;

import com.karmahostage.tzeentch.core.os.OsCommand;
import com.karmahostage.tzeentch.core.os.OsProcessBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdbExecutor {

    @Autowired
    private OsProcessBuilder adbProcessBuilder;

    public String execute(OsCommand osCommand) {
        return adbProcessBuilder.buildProcess(osCommand)
                .execute();
    }

}
