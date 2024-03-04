package com.aurora.githubintegration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @JsonProperty("login")
    String user_name;

    @JsonProperty("name")
    String display_name;

    @JsonProperty("avatar_url")
    String avatar;

    @JsonProperty("location")
    String geo_location;

    String email;

    String url;

    @JsonProperty("created_at")
    String created_at;

    List<GithubRepository> repos;
}
