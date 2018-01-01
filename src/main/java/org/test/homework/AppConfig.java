package org.test.homework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.AsyncRestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public AsyncRestTemplate restTemplate() {
        return new AsyncRestTemplate();
    }
}