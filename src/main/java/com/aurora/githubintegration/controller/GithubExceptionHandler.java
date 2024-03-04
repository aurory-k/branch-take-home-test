package com.aurora.githubintegration.controller;

import com.aurora.githubintegration.model.github.GithubException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GithubExceptionHandler {

    @ExceptionHandler(value = GithubException.class)
    protected ResponseEntity<GithubException> handleGithubExceptions(GithubException ex){

        return new ResponseEntity<>(ex, HttpStatusCode.valueOf(ex.getError_status()));
    }
}
