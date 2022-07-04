package com.kruna1pate1.pictionaryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PictionaryGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(PictionaryGameApplication.class, args);
    }

}
