package com.karmahostage.tzeentch.core.adb.process;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AdbProcess {

    private ProcessBuilder processBuilder;

    public AdbProcess(ProcessBuilder processBuilder) {
        this.processBuilder = processBuilder;
    }

    public List<String> execute() {
        try {
            Process p = processBuilder.start();
            p.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String _temp;
            List<String> outputLines = new ArrayList<>();
            while ((_temp = in.readLine()) != null) {
                outputLines.add(_temp);
            }
            return outputLines;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
