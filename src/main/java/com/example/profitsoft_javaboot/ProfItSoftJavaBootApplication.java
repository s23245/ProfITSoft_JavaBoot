package com.example.profitsoft_javaboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ProfItSoftJavaBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfItSoftJavaBootApplication.class, args);
    }

}
