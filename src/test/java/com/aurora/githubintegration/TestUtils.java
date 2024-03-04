package com.aurora.githubintegration;

import com.aurora.githubintegration.model.Repository;
import com.aurora.githubintegration.model.github.GithubRepositoryResponse;
import com.aurora.githubintegration.model.github.GithubUserResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {

    public static String githubUsername = "testName";

    public static GithubUserResponse buildGithubUserResponse(String name) {
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

    public static List<GithubRepositoryResponse> buildGithubRepositoryResponse(int amount) {
        List<GithubRepositoryResponse> listOfRepositories = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            GithubRepositoryResponse tempRepository = new GithubRepositoryResponse("repo" + i);
            listOfRepositories.add(tempRepository);
        }
        return listOfRepositories;
    }

    public static List<Repository> buildRepositories(int amount) {
        return buildGithubRepositoryResponse(amount)
                .stream()
                .map(githubRepository -> new Repository(
                                githubRepository.getRepository_name(),
                                "https://github.com/" + githubUsername + "/" + githubRepository.getRepository_name()
                        )
                ).collect(Collectors.toList());
    }
}
