package com.karmahostage.tzeentch.core.fuzzing;

import com.karmahostage.tzeentch.core.fuzzing.radamsa.Radamsa;
import com.karmahostage.tzeentch.core.os.OsProcessBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Fuzzing {

    @Autowired
    private OsProcessBuilder osProcessBuilder;

    public Radamsa radamsa() {
        return new Radamsa(osProcessBuilder);
    }
}
