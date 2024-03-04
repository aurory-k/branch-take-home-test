package com.aurora.githubintegration.client;

import com.aurora.githubintegration.TestUtils;
import com.aurora.githubintegration.model.github.GithubRepositoryResponse;
import com.aurora.githubintegration.model.github.GithubUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.List;

import static com.aurora.githubintegration.TestUtils.githubUsername;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GithubClientTest {
    @Mock
    RestTemplate restTemplateMock;
    GithubClient githubClient;

    @BeforeEach
    public void setup(){
        githubClient = new GithubClient(restTemplateMock);
    }

    @Test
    public void getUserData_whenSuccess_returnValidObject() {
        when(restTemplateMock.getForObject(
                "https://api.github.com/users/"+githubUsername,
                GithubUserResponse.class
        )).thenReturn(TestUtils.buildGithubUserResponse(githubUsername));

        GithubUserResponse response = githubClient.getUserData(githubUsername);
        assertThat(response).isNotNull();
        assertThat(response.getUser_name()).isEqualTo(githubUsername);
    }

    @Test()
    public void getUserData_whenFailure_throwException() {
        when(restTemplateMock.getForObject(
                "https://api.github.com/users/"+githubUsername,
                GithubUserResponse.class
        )).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong"));

        assertThatThrownBy(() -> githubClient.getUserData(githubUsername)).isInstanceOf(HttpClientErrorException.class);
    }

    @Test
    public void getUserRepositories_whenSuccess_returnValidObject() {
        when(restTemplateMock.exchange(
                "https://api.github.com/users/" + githubUsername + "/repos",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GithubRepositoryResponse>>() {}
        )).thenReturn(ResponseEntity.ok(TestUtils.buildGithubRepositoryResponse(3)));

        List<GithubRepositoryResponse> response = githubClient.getUserRepositories(githubUsername);
        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(3);
    }

    @Test()
    public void getUserRepositories_whenFailure_throwException() {
        when(restTemplateMock.exchange(
                "https://api.github.com/users/" + githubUsername + "/repos",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GithubRepositoryResponse>>() {}
        )).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong"));

        assertThatThrownBy(() -> githubClient.getUserRepositories(githubUsername)).isInstanceOf(HttpClientErrorException.class);
    }
}