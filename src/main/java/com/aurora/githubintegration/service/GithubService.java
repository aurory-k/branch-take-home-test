package com.aurora.githubintegration.service;

import com.aurora.githubintegration.client.GithubClient;
import com.aurora.githubintegration.model.Repository;
import com.aurora.githubintegration.model.github.GithubRepositoryResponse;
import com.aurora.githubintegration.model.github.GithubUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubService {
    @Autowired
    private final GithubClient githubClient;

    public GithubService(GithubClient githubClient){
        this.githubClient = githubClient;
    }

    public GithubUserResponse getUserData(String githubUsername){
        return githubClient.getUserData(githubUsername);
    }

    public List<Repository> getRepositoryData(String githubUsername){
        List<GithubRepositoryResponse> repositories = githubClient.getUserRepositories(githubUsername);
        return repositories.stream()
                .map(githubRepository ->
                        new Repository(
                                githubRepository.getRepository_name(),
                                "https://github.com/"+githubUsername+"/"+githubRepository.getRepository_name()
                        )
                ).collect(Collectors.toList());
    }

}
