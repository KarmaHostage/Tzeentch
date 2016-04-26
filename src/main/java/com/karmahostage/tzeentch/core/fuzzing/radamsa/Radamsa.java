package com.karmahostage.tzeentch.core.fuzzing.radamsa;

import com.karmahostage.tzeentch.core.FileTypes;
import com.karmahostage.tzeentch.core.os.OsCommand;
import com.karmahostage.tzeentch.core.os.OsProcessBuilder;
import org.springframework.util.Assert;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Radamsa {
    private String source;
    private String destination;
    private FileTypes fileType;
    private OsProcessBuilder osProcessBuilder;

    public Radamsa(OsProcessBuilder osProcessBuilder) {
        this.osProcessBuilder = osProcessBuilder;
    }

    public Radamsa withSource(String source) {
        this.source = source;
        return this;
    }

    public Radamsa withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public Radamsa withFileType(FileTypes fileType) {
        this.fileType = fileType;
        return this;
    }

    public File fuzz() {
        osProcessBuilder.buildProcess(
                new OsCommand("radamsa", source + " > " + destination)
        ).execute();
        Assert.isTrue(Files.exists(Paths.get(destination)));
        return new File(destination);
    }

}
