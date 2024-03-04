package com.aurora.githubintegration.controller;

import com.aurora.githubintegration.model.UserResponse;
import com.aurora.githubintegration.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GithubController {
    @Autowired
    private final GithubService githubService;

    public GithubController(GithubService githubService){

        this.githubService = githubService;
    }

    @GetMapping("/user/{githubUsername}")
    public ResponseEntity<UserResponse> getUserData(@PathVariable String githubUsername){
        UserResponse userData = githubService.getUserData(githubUsername);
        return ResponseEntity.ok(userData);
    }
}
