package com.aurora.githubintegration.controller;

import com.aurora.githubintegration.model.Repository;
import com.aurora.githubintegration.model.UserDataResponse;
import com.aurora.githubintegration.model.github.GithubUserResponse;
import com.aurora.githubintegration.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GithubController {
    @Autowired
    private final GithubService githubService;

    public GithubController(GithubService githubService){
        this.githubService = githubService;
    }

    @GetMapping("/user/{githubUsername}")
    public ResponseEntity<UserDataResponse> getUserData(@PathVariable String githubUsername){
        GithubUserResponse githubUserData = githubService.getUserData(githubUsername);
        List<Repository> repositories = githubService.getRepositoryData(githubUsername);

        UserDataResponse userDataResponse = new UserDataResponse(
                githubUserData.getUser_name(),
                githubUserData.getDisplay_name(),
                githubUserData.getAvatar(),
                githubUserData.getGeo_location(),
                githubUserData.getEmail(),
                githubUserData.getUrl(),
                githubUserData.getCreated_at(),
                repositories
        );

        return ResponseEntity.ok(userDataResponse);
    }
}
