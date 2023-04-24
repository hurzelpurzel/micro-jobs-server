package com.andreidodu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroJobsApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(MicroJobsApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
