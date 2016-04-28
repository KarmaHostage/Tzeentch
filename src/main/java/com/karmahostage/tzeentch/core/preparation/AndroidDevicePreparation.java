package com.karmahostage.tzeentch.core.preparation;

import com.karmahostage.tzeentch.core.FileTypes;
import com.karmahostage.tzeentch.core.adb.Adb;
import com.karmahostage.tzeentch.core.adb.executor.AdbExecutor;
import com.karmahostage.tzeentch.core.os.OsCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.stream.Stream;

@Component
class AndroidDevicePreparation {

    @Value("${com.karmahostage.tzeentch.local.workingdirectory}")
    private String workingDirectory;

    @Value("${com.karmahostage.tzeentch.android.workingdirectory}")
    private String androidWorkingDirectory;

    @Autowired
    private AdbExecutor adb;

    void sendReferenceFiles() {
        Stream.of(FileTypes.values())
                .forEach(this::pushfile);
    }

    private void pushfile(FileTypes fileTypes) {
        OsCommand osCommand = Adb
                .push()
                .withSource(new File(inputFile(fileTypes)))
                .withDestination(androidWorkingDirectory + "/reference." + fileTypes.getExtension())
                .createCommand();
        System.out.println(
                adb.execute(osCommand)
        );
    }

    private String inputFile(FileTypes filetype) {
        return workingDirectory + File.separator + "reference_format" + File.separator + filetype.getExtension() + File.separator + "input." + filetype.getExtension();
    }

    private String encodedDirectory() {
        return androidWorkingDirectory.replace("/", "//");
    }
}
