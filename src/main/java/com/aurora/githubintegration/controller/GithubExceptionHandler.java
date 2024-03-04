package com.aurora.githubintegration.controller;

import com.aurora.githubintegration.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GithubExceptionHandler {

    @ExceptionHandler(value = HttpClientErrorException.class)
    protected ResponseEntity<ErrorResponse> handleRestTemplateExceptions(HttpClientErrorException ex){

        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ex.getStatusCode().value()), ex.getStatusCode());
    }
}
