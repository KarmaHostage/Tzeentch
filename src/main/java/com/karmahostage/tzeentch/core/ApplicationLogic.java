package com.karmahostage.tzeentch.core;

import com.karmahostage.tzeentch.core.adb.Adb;
import com.karmahostage.tzeentch.core.fuzzing.Fuzzing;
import com.karmahostage.tzeentch.core.os.OsCommand;
import com.karmahostage.tzeentch.core.os.OsProcessBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ApplicationLogic {

    @Autowired
    private Fuzzing fuzzing;
    @Autowired
    private OsProcessBuilder osProcessBuilder;

    @Value("${com.karmahostage.tzeentch.local.workingdirectory}")
    private String workingDirectory;
    @Value("${com.karmahostage.tzeentch.android.workingdirectory}")
    private String androidWorkingDirectory;


    public void iterate(FileTypes fileType, int times) {
        initiateNormalFlow(fileType);
        IntStream.range(0, times)
                .forEach(element -> initiateFuzzFlow(fileType, "0"));
    }

    private void initiateNormalFlow(FileTypes fileType) {
        startFile(fileType, "reference");
    }

    private void initiateFuzzFlow(FileTypes fileType, String initialProcessId) {
        String processId = initialProcessId;
        pushFile(fuzz(fileType), fileType);
        clearLogging();
        startFile(fileType, "fuzzed");
        waitUntilStartOrCrash(processId);
        checkApplication("com.android.gallery3d");
        reportLogging(fetchLogging());
    }

    private void checkApplication(String intentName) {
        //TODO
    }

    private void waitUntilStartOrCrash(String processId) {
        //TODO
    }

    private void reportLogging(String log) {
        System.out.println(log);
    }

    private String fetchLogging() {
        OsCommand command = Adb.shell().logcat().dump();
        return osProcessBuilder.buildProcess(command)
                .execute()
                .stream()
                .collect(Collectors.joining("\n"));
    }

    private void clearLogging() {
        OsCommand command = Adb.shell().logcat().clear();
        osProcessBuilder.buildProcess(command)
                .execute()
                .stream()
                .forEach(System.out::println);
    }

    private void startFile(FileTypes fileType, String fileName) {
        OsCommand command = Adb.shell().am().start().viewIntent()
                .withFileLocation(androidWorkingDirectory + File.separator + fileName + "." + fileType.getExtension())
                .withFileType(fileType.getMediatype())
                .toCommand();
        osProcessBuilder.buildProcess(command)
                .execute()
                .stream()
                .forEach(System.out::println);
    }

    private void pushFile(File fuzzedFile, FileTypes filetype) {
        OsCommand command = Adb.push().withSource(fuzzedFile)
                .withDestination(androidWorkingDirectory + File.separator + "fuzzed." + filetype.getExtension())
                .createCommand();
        osProcessBuilder.buildProcess(command)
                .execute()
                .stream()
                .forEach(System.out::println);
    }


    private File fuzz(FileTypes fileTypes) {
        return fuzzing.radamsa()
                .withSource(workingDirectory + File.separator + "learning" + File.separator + FileTypes.JPG.getExtension() + File.separator + "*." + FileTypes.JPG.getExtension())
                .withDestination(workingDirectory + File.separator + "fuzzing" + File.separator + FileTypes.JPG.getExtension() + File.separator + "fuzzed." + FileTypes.JPG.getExtension())
                .fuzz();
    }

}
