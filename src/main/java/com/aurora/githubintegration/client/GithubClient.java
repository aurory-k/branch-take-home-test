package com.aurora.githubintegration.client;

import com.aurora.githubintegration.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GithubClient {

    @Autowired
    private RestTemplate restTemplate;

    public UserResponse getUserData(String githubUsername){
        return restTemplate.getForObject("https://api.github.com/users/"+githubUsername, UserResponse.class);
    }

}
