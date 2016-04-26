package com.karmahostage.tzeentch.core.adb;

import com.karmahostage.tzeentch.core.adb.model.AdbCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdbExecutor {

    @Autowired
    private AdbProcessBuilder adbProcessBuilder;

    public void execute(AdbCommand adbCommand) {
        adbProcessBuilder.buildProcess(adbCommand)
                .execute()
                .stream()
                .forEach(System.out::println);
    }

}
