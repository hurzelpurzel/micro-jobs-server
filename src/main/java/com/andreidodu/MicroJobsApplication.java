package com.andreidodu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MicroJobsApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(MicroJobsApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
