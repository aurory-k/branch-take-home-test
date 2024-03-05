package com.aurora.githubintegration.service;

import com.aurora.githubintegration.TestUtils;
import com.aurora.githubintegration.client.GithubClient;
import com.aurora.githubintegration.model.Repository;
import com.aurora.githubintegration.model.UserCachingData;
import com.aurora.githubintegration.model.github.GithubRepositoryResponse;
import com.aurora.githubintegration.model.github.GithubUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

import static com.aurora.githubintegration.TestUtils.*;
import static com.aurora.githubintegration.TestUtils.githubUsername;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GithubServiceTest {
    @Mock
    private GithubClient githubClientMock;

    private HashMap<String, UserCachingData> userDataCache = new HashMap<>();

    private GithubService githubService;

    private String eTag = "W/\"someETag\"";
    private String lastModified = String.valueOf(1709601354);

    @BeforeEach
    public void setup() {
        userDataCache.clear();
        githubService = new GithubService(githubClientMock, userDataCache);
    }

    @Test
    public void getRepositoryData_withNoCache_mapsToRepositoryModel_andUpdatesCache() {

        ResponseEntity<List<GithubRepositoryResponse>> mockResponse = ResponseEntity
                .status(200)
                .header("ETag", eTag)
                .header("Last-Modified", lastModified)
                .body(buildGithubRepositoryResponse(2));

        when(githubClientMock.getUserRepositories(eq(githubUsername), any()))
                .thenReturn(mockResponse);

        assertThat(userDataCache.containsKey(githubUsername+"-repo")).isFalse();

        List<Repository> mappedRepositories = githubService.getRepositoryData(githubUsername);
        assertThat(mappedRepositories).isNotNull();
        assertThat(mappedRepositories.size()).isEqualTo(2);
        assertThat(mappedRepositories.get(0).getRepository_name()).isEqualTo("repo0");
        assertThat(mappedRepositories.get(1).getRepository_name()).isEqualTo("repo1");

        assertThat(userDataCache.containsKey(githubUsername+"-repo")).isTrue();
        assertThat(userDataCache.get(githubUsername+"-repo").getETag()).isEqualTo(eTag.substring(2));
        assertThat(userDataCache.get(githubUsername+"-repo").getLastModified()).isNotNull();
    }

    @Test
    public void getUserData_withCache_sendsUpdatedHeaders() {
        userDataCache.put(githubUsername+"-user", new UserCachingData(eTag.substring(2), lastModified));
        ResponseEntity<GithubUserResponse> mockResponse = ResponseEntity
                .status(200)
                .header("ETag", eTag)
                .header("Last-Modified", lastModified)
                .body(buildGithubUserResponse(githubUsername));

        HttpHeaders expectedHeaders = new HttpHeaders();
        expectedHeaders.add("Accept", "application/vnd.github+json");
        expectedHeaders.add("If-Modified-Since", lastModified);
        expectedHeaders.add("If-None-Match", eTag.substring(2));

        HttpEntity<Void> entityWithHeaders = new HttpEntity<>(expectedHeaders);

        when(githubClientMock.getUserData(githubUsername, entityWithHeaders))
                .thenReturn(mockResponse);

        assertThat(userDataCache.containsKey(githubUsername+"-user")).isTrue();

        GithubUserResponse mappedRepositories = githubService.getUserData(githubUsername);
        assertThat(mappedRepositories).isNotNull();
        assertThat(mappedRepositories.getUser_name()).isEqualTo(githubUsername);

        assertThat(userDataCache.get(githubUsername+"-user").getETag()).isEqualTo(eTag.substring(2));
        assertThat(userDataCache.get(githubUsername+"-user").getLastModified()).isNotNull();
    }

}