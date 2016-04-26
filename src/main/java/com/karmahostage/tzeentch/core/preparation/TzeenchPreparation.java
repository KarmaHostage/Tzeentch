package com.karmahostage.tzeentch.core.preparation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TzeenchPreparation {

    @Autowired
    private LocalMachinePreparation localMachinePreparation;
    @Autowired
    private AndroidDevicePreparation androidDevicePreparation;

    @PostConstruct
    public void prepareEnvironment() {
        localMachinePreparation.prepareLocalFolders();
        androidDevicePreparation.sendReferenceFiles();
    }

}
