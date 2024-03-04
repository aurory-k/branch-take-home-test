package com.aurora.githubintegration.client;

import com.aurora.githubintegration.TestUtils;
import com.aurora.githubintegration.model.github.GithubUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GithubClientTest {
    @Mock
    RestTemplate restTemplateMock = mock(RestTemplate.class);
    GithubClient githubClient;

    String githubUsername = "testName";

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
}