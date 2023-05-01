package com.andreidodu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@EnableJpaAuditing
public class MicroJobsApplication {

    private final static String FILES_DIRECTORY = "./files";

    public static void main(String[] args) {
        try {
            SpringApplication.run(MicroJobsApplication.class, args);
            createFilesDirectoryIfNotExists();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createFilesDirectoryIfNotExists() throws IOException {
        Path path = Paths.get(FILES_DIRECTORY);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            System.out.println("Created `" + FILES_DIRECTORY + "` directory :)");
        }
    }

}
