package com.aurora.githubintegration.service;

import com.aurora.githubintegration.client.GithubClient;
import com.aurora.githubintegration.model.Repository;
import com.aurora.githubintegration.model.UserCachingData;
import com.aurora.githubintegration.model.github.GithubRepositoryResponse;
import com.aurora.githubintegration.model.github.GithubUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubService {

    private final GithubClient githubClient;

    private final HashMap<String, UserCachingData> dataCache;

    @Autowired
    public GithubService(
            GithubClient githubClient,
            HashMap<String, UserCachingData> dataCache
    ) {
        this.githubClient = githubClient;
        this.dataCache = dataCache;
    }

    public GithubUserResponse getUserData(String githubUsername) {
        HttpEntity<Void> entityWithHeaders = createHeaders(githubUsername+"-user");
        ResponseEntity<GithubUserResponse> responseEntity = githubClient.getUserData(githubUsername, entityWithHeaders);
        updateCache(responseEntity, githubUsername+"-user");

        return responseEntity.getBody();
    }

    public List<Repository> getRepositoryData(String githubUsername) {
        HttpEntity<Void> entityWithHeaders = createHeaders(githubUsername+"-repo");
        ResponseEntity<List<GithubRepositoryResponse>> responseEntity = githubClient.getUserRepositories(githubUsername, entityWithHeaders);
        updateCache(responseEntity, githubUsername+"-repo");

        return responseEntity.getBody().stream()
                .map(githubRepository ->
                        new Repository(
                                githubRepository.getRepository_name(),
                                "https://github.com/" + githubUsername + "/" + githubRepository.getRepository_name()
                        )
                ).collect(Collectors.toList());
    }

    public HttpEntity<Void> createHeaders(String githubUsername){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/vnd.github+json");

        if(dataCache.containsKey(githubUsername)){
            UserCachingData userCachingData = dataCache.get(githubUsername);
            headers.add("If-Modified-Since", String.valueOf(userCachingData.getLastModified()));
            headers.add("If-None-Match", userCachingData.getETag());
        }
        return new HttpEntity<>(headers);
    }

    public void updateCache(ResponseEntity<?> entityWithHeaders, String githubUsername){
        HttpHeaders responseHeaders = entityWithHeaders.getHeaders();
        String lastModifiedAsString = Instant.ofEpochMilli(responseHeaders.getLastModified()).atZone(ZoneId.of("GMT")).toString();
        dataCache.putIfAbsent(githubUsername, new UserCachingData(responseHeaders.getETag().substring(2), lastModifiedAsString));
    }

}
