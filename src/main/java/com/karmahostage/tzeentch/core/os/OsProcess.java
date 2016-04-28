package com.karmahostage.tzeentch.core.os;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OsProcess {

    private ProcessBuilder processBuilder;

    OsProcess(ProcessBuilder processBuilder) {
        this.processBuilder = processBuilder;
    }

    public String execute() {
        try {
            Process p = processBuilder.start();
            p.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String _temp;
            List<String> outputLines = new ArrayList<>();
            while ((_temp = in.readLine()) != null) {
                outputLines.add(_temp);
            }
            return outputLines
                    .stream()
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
