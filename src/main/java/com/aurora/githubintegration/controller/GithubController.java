package com.aurora.githubintegration.controller;

import com.aurora.githubintegration.model.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GithubController {

    @GetMapping("/user/{githubUsername}")
    public ResponseEntity<UserResponse> getUserData(@PathVariable String githubUsername){
        return ResponseEntity.ok(new UserResponse());
    }
}
