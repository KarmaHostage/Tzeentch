package com.karmahostage.tzeentch.core.preparation;

import com.karmahostage.tzeentch.core.FileTypes;
import com.karmahostage.tzeentch.core.fuzzing.Fuzzing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
public class TzeenchPreparation {

    @Autowired
    private LocalMachinePreparation localMachinePreparation;
    @Autowired
    private AndroidDevicePreparation androidDevicePreparation;

    @Value("${com.karmahostage.tzeentch.local.workingdirectory}")
    private String workingDirectory;

    @PostConstruct
    public void prepareEnvironment() {
        localMachinePreparation.prepareLocalFolders();
        androidDevicePreparation.sendReferenceFiles();
    }

}
