package com.karmahostage.tzeentch.core;

import com.karmahostage.tzeentch.core.adb.Adb;
import com.karmahostage.tzeentch.core.fuzzing.Fuzzing;
import com.karmahostage.tzeentch.core.os.OsCommand;
import com.karmahostage.tzeentch.core.os.OsProcessBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.apache.commons.lang3.math.NumberUtils.isNumber;

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
        String applicationId = initiateNormalFlow(fileType);
        startFuzzing(fileType, applicationId);
        IntStream.range(0, times)
                .forEach(element -> initiateFuzzFlow(fileType, "0"));
    }

    private void startFuzzing(FileTypes fileType, String applicationId) {
        initiateFuzzFlow(fileType, applicationId);
    }

    private String initiateNormalFlow(FileTypes fileType) {
        startFile(fileType, "reference");
        return getApplicationId("com.android.gallery3d");
    }

    private void initiateFuzzFlow(FileTypes fileType, String initialProcessId) {
        pushBack();
        pushFile(fuzz(fileType), fileType);
        clearLogging();
        startFile(fileType, "fuzzed");
        waitUntilStartOrCrash(initialProcessId);
        String applicationId = getApplicationId("com.android.gallery3d");
        if (applicationId.equals("") || !applicationId.equals(initialProcessId)) {
            System.out.println("---> application crashed! send logs to backend");
            String newApplicationId = initiateNormalFlow(fileType);
            initiateFuzzFlow(fileType, newApplicationId);
        } else {
            System.out.println("---> application not crashed, send logs to backend for SIG checks");
            sendLogging(fetchLogging());
            initiateFuzzFlow(fileType, applicationId);
        }

    }

    private void pushBack() {
        osProcessBuilder.buildProcess(
                Adb.shell().input().keyEvent().keyEvent("4")
        ).execute();
    }

    private String getApplicationId(String intentName) {
        OsCommand command = Adb.shell().ps().andLookForProcessName(intentName);
        List<String> entry = Stream.of(osProcessBuilder.buildProcess(command)
                .execute()
                .stream()
                .collect(Collectors.joining("\n"))
                .split(" ")
        )
                .filter(element -> !element.isEmpty())
                .collect(Collectors.toList());

        if (entry.size() > 1 && !entry.get(1).isEmpty() && isNumber(entry.get(1))) {
            return entry.get(1);
        }
        return "";
    }

    private void waitUntilStartOrCrash(String processId) {
        for (int i = 0; i < 10; i++) {
            List<String> state = Stream.of(osProcessBuilder.buildProcess(
                    Adb
                            .shell()
                            .cat()
                            .doCat(String.format("/proc/%s/status | grep State", processId))
                    ).execute()
                            .stream()
                            .collect(Collectors.joining("\n"))
                            .split(" ")
            ).filter(element -> !element.isEmpty())
                    .collect(Collectors.toList());

            if (state != null && state.size() > 0 && state.get(0).equals("State:\tS")) {
                return;
            } else {
                System.out.println("Process is still starting up or has crashed, going to bed");
                sleep();
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (Exception ex) {
            System.out.println("sleep interrupted");
        }
    }

    private void sendLogging(String log) {
        //send it to backend
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
                .withSource(workingDirectory + File.separator + "learning" + File.separator + fileTypes.getExtension() + File.separator + "*." + fileTypes.getExtension())
                .withDestination(workingDirectory + File.separator + "fuzzing" + File.separator + fileTypes.getExtension() + File.separator + "fuzzed." + fileTypes.getExtension())
                .fuzz();
    }

}
