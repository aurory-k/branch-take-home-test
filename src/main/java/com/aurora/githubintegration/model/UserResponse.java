package com.aurora.githubintegration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    String userName;
    String displayName;
    String avatar;
    String geoLocation;
    String email;
    String url;
    String createdAt;
    List<GithubRepository> repos;
}
