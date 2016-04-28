package com.karmahostage.tzeentch;

import com.karmahostage.tzeentch.core.ApplicationLogic;
import com.karmahostage.tzeentch.core.FileTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Tzeentch implements CommandLineRunner {
    @Autowired
    private ApplicationLogic applicationLogic;

    public static void main(String[] args) {
        SpringApplication.run(Tzeentch.class).close();
    }

    @Override
    public void run(String... args) throws Exception {
        applicationLogic.iterate(FileTypes.JPG, 1);
    }
}
