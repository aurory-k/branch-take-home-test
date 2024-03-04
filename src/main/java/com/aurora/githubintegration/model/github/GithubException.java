package com.aurora.githubintegration.model.github;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubException extends Exception{
    String error_message;
    int error_status;
}
