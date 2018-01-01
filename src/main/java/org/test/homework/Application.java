package org.test.homework;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan
@EnableAutoConfiguration
@PropertySource(value = "classpath:application.properties")
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}