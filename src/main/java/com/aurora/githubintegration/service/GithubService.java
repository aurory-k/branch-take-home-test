package com.aurora.githubintegration.service;

import com.aurora.githubintegration.client.GithubClient;
import com.aurora.githubintegration.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GithubService {
    @Autowired
    private final GithubClient githubClient;

    public GithubService(GithubClient githubClient){
        this.githubClient = githubClient;
    }

    public UserResponse getUserData(String githubUsername){
        return githubClient.getUserData(githubUsername);
    }

}
