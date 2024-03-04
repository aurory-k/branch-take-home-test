package com.aurora.githubintegration.service;

import com.aurora.githubintegration.TestUtils;
import com.aurora.githubintegration.client.GithubClient;
import com.aurora.githubintegration.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.aurora.githubintegration.TestUtils.githubUsername;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GithubServiceTest {
    @Mock
    private GithubClient githubClientMock;

    private GithubService githubService;

    @BeforeEach
    public void setup() {
        githubService = new GithubService(githubClientMock);
    }

    @Test
    public void getRepositoryData_mapsToRepositoryModel(){
        when(githubClientMock.getUserRepositories(githubUsername))
                .thenReturn(TestUtils.buildGithubRepositoryResponse(2));

        List<Repository> mappedRepositories = githubService.getRepositoryData(githubUsername);
        assertThat(mappedRepositories).isNotNull();
        assertThat(mappedRepositories.size()).isEqualTo(2);
        assertThat(mappedRepositories.get(0).getRepository_name()).isEqualTo("repo0");
        assertThat(mappedRepositories.get(1).getRepository_name()).isEqualTo("repo1");
    }

}