package com.org.olympiccourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OlympicCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(OlympicCourseApplication.class, args);
    }

}
