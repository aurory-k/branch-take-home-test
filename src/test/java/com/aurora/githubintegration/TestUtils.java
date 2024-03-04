package com.aurora.githubintegration;

import com.aurora.githubintegration.model.github.GithubUserResponse;

public class TestUtils {

    public static GithubUserResponse buildGithubUserResponse(String name){
        return new GithubUserResponse(
                name,
                "",
                "",
                "",
                null,
                "",
                ""
        );
    }
}
