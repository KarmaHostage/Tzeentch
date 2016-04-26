package com.karmahostage.tzeentch.core.preparation;

import com.karmahostage.tzeentch.core.FileTypes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

@Component
class LocalMachinePreparation {

    private static final String INTERMEDIATE_FUZZING_DIRECTORY = "fuzzing";
    private static final String CRASHES_DIRECTORY = "crashes";
    private static final String LEARNING_DIRECTORY = "learning";
    private static final String REFERENCE_FORMAT = "reference_format";

    @Value("${com.karmahostage.tzeentch.local.workingdirectory}")
    private String workingDirectory;

    void prepareLocalFolders() {
        Assert.hasText(workingDirectory, "Please provide a working directory in the application.yml");
        assertWorkingDirectoryWritable(workingDirectory);
        createSubDirectory(INTERMEDIATE_FUZZING_DIRECTORY);
        createSubDirectory(CRASHES_DIRECTORY);
        createSubDirectory(LEARNING_DIRECTORY);
        createSubDirectory(REFERENCE_FORMAT);

        prepareReferenceFormats();
    }

    private void prepareReferenceFormats() {
        try {
            Stream.of(FileTypes.values())
                    .forEach(filetype -> {
                        ClassPathResource referenceFileResource = new ClassPathResource("reference_format/input." + filetype.getExtension());
                        try {
                            InputStream input = referenceFileResource.getInputStream();
                            FileOutputStream output = new FileOutputStream(
                                    new File(workingDirectory + File.separator + REFERENCE_FORMAT + File.separator + filetype.getExtension(), "input." + filetype.getExtension())
                            );
                            FileCopyUtils.copy(input, output);
                        } catch (IOException io) {
                            throw new IllegalArgumentException("unable to copy reference formats");
                        }
                    });
        } catch (Exception ex) {
            throw new IllegalArgumentException("unable to copy reference files to your working directory");
        }
    }

    private void assertWorkingDirectoryWritable(String workingDirectory) {
        File file = new File(workingDirectory);
        if (!file.exists()) {
            Assert.isTrue(file.mkdir(), "unable to create working directory");
        }
        Assert.isTrue(file.exists(), "working directory should exist");
        Assert.isTrue(file.isDirectory(), "working directory should be a folder");
        try {
            File writableCheck = new File(file, ".Tzeentch");
            Assert.isTrue(writableCheck.exists() || writableCheck.createNewFile(), workingDirectory + " should be writable");
        } catch (IOException io) {
            throw new IllegalArgumentException(workingDirectory + " is not writable!");
        }
    }

    private void createSubDirectory(String subfolder) {
        File fuzzingDirectory = new File(new File(workingDirectory), subfolder);
        if (!fuzzingDirectory.exists()) {
            Assert.isTrue(fuzzingDirectory.mkdir(), workingDirectory + " should be writable");
        } else {
            Assert.isTrue(fuzzingDirectory.isDirectory(), subfolder + " directory should be writable!");
        }
        Stream.of(FileTypes.values())
                .forEach(
                        filetype -> {
                            File extensionSubDirectory = new File(fuzzingDirectory, filetype.getExtension());
                            Assert.isTrue((extensionSubDirectory.exists() && extensionSubDirectory.isDirectory()) || extensionSubDirectory.mkdir());
                        }
                );
    }
}
