package org.kdubij.githubclient.api.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kdubij.githubclient.client.exception.client.GithubClientConnectionException;
import org.kdubij.githubclient.client.exception.client.GithubClientException;
import org.kdubij.githubclient.client.exception.client.GithubRepoNotFoundException;
import org.kdubij.githubclient.client.exception.server.GithubServerException;
import org.kdubij.githubclient.client.exception.server.GithubServiceUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller for mapping exceptions
 */
@RestControllerAdvice
public class ExceptionController {
    private static final Logger logger = LogManager.getLogger(ExceptionController.class);

    @ExceptionHandler(GithubClientException.class)
    public ResponseEntity<ErrorResponseDto> handleGithubClientException(GithubClientException e) {
        logger.error("GithubClientException occurred with message: {}", e.getLocalizedMessage());
        ErrorResponseDto responseDto = new ErrorResponseDto(ErrorCode.CLIENT_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

    @ExceptionHandler(GithubRepoNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleGithubClientException(GithubRepoNotFoundException e) {
        logger.warn("GithubRepoNotFoundException occurred with message: {}", e.getLocalizedMessage());
        ErrorResponseDto responseDto = new ErrorResponseDto(ErrorCode.REPOSITORY_NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    @ExceptionHandler(GithubClientConnectionException.class)
    public ResponseEntity<ErrorResponseDto> handleGithubClientException(GithubClientConnectionException e) {
        logger.error("GithubClientConnectionException occurred with message: {}", e.getLocalizedMessage());
        ErrorResponseDto responseDto = new ErrorResponseDto(ErrorCode.CLIENT_CONNECTION_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

    @ExceptionHandler(GithubServiceUnavailableException.class)
    public ResponseEntity<ErrorResponseDto> handleGithubServiceUnavailableException(GithubServiceUnavailableException e) {
        logger.error("GithubServiceUnavailableException occurred with message: {}", e.getLocalizedMessage());
        ErrorResponseDto responseDto = new ErrorResponseDto(ErrorCode.GITHUB_SERVICE_UNAVAILABLE, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

    @ExceptionHandler(GithubServerException.class)
    public ResponseEntity<ErrorResponseDto> handleGithubServiceUnavailableException(GithubServerException e) {
        logger.error("GithubServerException occurred with message: {}", e.getLocalizedMessage());
        ErrorResponseDto responseDto = new ErrorResponseDto(ErrorCode.GITHUB_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        logger.error("Unexpected exception occurred with message: {}", e.getLocalizedMessage());
        ErrorResponseDto responseDto = new ErrorResponseDto(ErrorCode.UNEXPECTED_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

}
