package com.aurora.githubintegration.service;

import com.aurora.githubintegration.TestUtils;
import com.aurora.githubintegration.client.GithubClient;
import com.aurora.githubintegration.model.Repository;
import com.aurora.githubintegration.model.UserCachingData;
import com.aurora.githubintegration.model.github.GithubRepositoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @Mock
    private HashMap<String, UserCachingData> userDataCache;

    private GithubService githubService;

    @BeforeEach
    public void setup() {
        githubService = new GithubService(githubClientMock, userDataCache);
    }

    @Test
    public void getRepositoryData_mapsToRepositoryModel() {

        ResponseEntity<List<GithubRepositoryResponse>> mockResponse = ResponseEntity
                .status(200)
                .header("ETag", "123")
                .header("Last-Modified", "123")
                .body(buildGithubRepositoryResponse(2));

        when(githubClientMock.getUserRepositories(eq(githubUsername), any()))
                .thenReturn(mockResponse);

        List<Repository> mappedRepositories = githubService.getRepositoryData(githubUsername);
        assertThat(mappedRepositories).isNotNull();
        assertThat(mappedRepositories.size()).isEqualTo(2);
        assertThat(mappedRepositories.get(0).getRepository_name()).isEqualTo("repo0");
        assertThat(mappedRepositories.get(1).getRepository_name()).isEqualTo("repo1");
    }

}