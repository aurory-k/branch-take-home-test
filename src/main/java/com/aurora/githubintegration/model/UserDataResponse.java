package com.aurora.githubintegration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(value = {"user_name", "display_name", "avatar", "geo_location", "email", "url", "created_at", "repos"})
public class UserDataResponse {
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

    List<Repository> repos;
}
