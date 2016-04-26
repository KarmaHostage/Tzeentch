package com.karmahostage.tzeentch.core.os;

import java.util.Arrays;

public interface OsProcessBuilder {

    OsProcess buildProcess(OsCommand osCommand);

    default <T> T[] concat(T[] first, T... second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

}
