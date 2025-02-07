package com.everBuy.EverBuy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProductConfig {

    @Bean
    RestTemplate restTemplate(){return new RestTemplate();}
}
