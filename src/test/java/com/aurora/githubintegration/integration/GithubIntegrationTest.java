package com.aurora.githubintegration.integration;

import com.aurora.githubintegration.client.GithubClient;
import com.aurora.githubintegration.model.github.GithubUserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import static com.aurora.githubintegration.TestUtils.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GithubIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    GithubClient githubClient;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void getUserData_whenGivenValidInput_returnsUserData() throws Exception {

        GithubUserResponse githubUserResponse = buildGithubUserResponse(githubUsername);

        when(githubClient.getUserData(githubUsername, null)).thenReturn(ResponseEntity.ok(githubUserResponse));
        when(githubClient.getUserRepositories(githubUsername, null)).thenReturn(ResponseEntity.ok(buildGithubRepositoryResponse(3)));

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/user/" + githubUsername))
                .andExpect(status().isOk()
        );
    }

    @Test
    public void getUserData_whenGithubApiError_returnsError() throws Exception {
        when(githubClient.getUserData(githubUsername, null)).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong"));

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/user/" + githubUsername))
                .andExpect(status().is5xxServerError());
    }
}
