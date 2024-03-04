package com.aurora.githubintegration.controller;

import com.aurora.githubintegration.model.UserDataResponse;
import com.aurora.githubintegration.service.GithubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.aurora.githubintegration.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GithubControllerTest {

    @Mock
    GithubService githubServiceMock;

    GithubController githubController;

    @BeforeEach
    public void setup(){
        githubController = new GithubController(githubServiceMock);
    }

    @Test
    public void getUserData_whenValidData_returnUserDataResponse(){
        when(githubServiceMock.getUserData(githubUsername)).thenReturn(
                buildGithubUserResponse(githubUsername)
        );

        when(githubServiceMock.getRepositoryData(githubUsername)).thenReturn(
                buildRepositories(3)
        );

        UserDataResponse userDataResponse = githubController.getUserData(githubUsername).getBody();
        assertThat(userDataResponse).isNotNull();
        assertThat(userDataResponse.getUser_name()).isEqualTo(githubUsername);
    }

}