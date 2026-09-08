package org.example.pixdesfio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PixdesfioApplication {

    public static void main(String[] args) {
        SpringApplication.run(PixdesfioApplication.class, args);
    }

}
