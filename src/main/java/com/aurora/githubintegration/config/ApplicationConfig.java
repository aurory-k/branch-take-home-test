package com.aurora.githubintegration.config;

import com.aurora.githubintegration.model.UserCachingData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Configuration
public class ApplicationConfig {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public HashMap<String, UserCachingData> getUserDataCache(){
        return new HashMap<String, UserCachingData>();
    }
}
