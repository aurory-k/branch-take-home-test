package com.aurora.githubintegration;

import com.aurora.githubintegration.model.github.GithubRepositoryResponse;
import com.aurora.githubintegration.model.github.GithubUserResponse;

import java.util.ArrayList;
import java.util.List;

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

    public static List<GithubRepositoryResponse> buildGithubRepositoryResponse(int amount){
        List<GithubRepositoryResponse> listOfRepositories = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            GithubRepositoryResponse tempRepository= new GithubRepositoryResponse("repo"+i);
            listOfRepositories.add(tempRepository);
        }
        return listOfRepositories;
    }
}
