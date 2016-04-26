package com.karmahostage.tzeentch.core.adb;

import com.karmahostage.tzeentch.core.adb.model.AdbCommand;
import com.karmahostage.tzeentch.core.adb.model.AdbProcess;

import java.util.Arrays;

public interface AdbProcessBuilder {

    AdbProcess buildProcess(AdbCommand adbCommand);

    default <T> T[] concat(T[] first, T... second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

}
