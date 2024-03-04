package com.aurora.githubintegration.client;

import com.aurora.githubintegration.model.github.GithubUserResponse;
import com.aurora.githubintegration.model.github.GithubRepositoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
public class GithubClient {

    private final RestTemplate restTemplate;

    @Autowired
    public GithubClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GithubUserResponse getUserData(String githubUsername) {
        return restTemplate.getForObject(
                "https://api.github.com/users/" + githubUsername,
                GithubUserResponse.class
        );
    }

    public List<GithubRepositoryResponse> getUserRepositories(String githubUsername) {
        return restTemplate.exchange(
                "https://api.github.com/users/" + githubUsername + "/repos",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GithubRepositoryResponse>>() {
                }
        ).getBody();
    }

}
