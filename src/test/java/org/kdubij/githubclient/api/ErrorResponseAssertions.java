package org.kdubij.githubclient.api;

import org.kdubij.githubclient.api.exception.ErrorCode;
import org.kdubij.githubclient.api.exception.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorResponseAssertions {
    private ResponseEntity<ErrorResponseDto> responseEntity;

    private ErrorResponseAssertions(ResponseEntity<ErrorResponseDto> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public static ErrorResponseAssertions assertResponse(ResponseEntity<ErrorResponseDto> responseEntity) {
        return new ErrorResponseAssertions(responseEntity);
    }

    public ErrorResponseAssertions withStatus(HttpStatus status) {
        assertThat(responseEntity.getStatusCode()).isEqualTo(status);
        return this;
    }

    public ErrorResponseAssertions withType(ErrorCode type) {
        assertThat(responseEntity.getBody().getType()).isEqualTo(type);
        return this;
    }

    public ErrorResponseAssertions withCode(int code) {
        assertThat(responseEntity.getBody().getCode()).isEqualTo(code);
        return this;
    }

    public ErrorResponseAssertions withMessage(String message) {
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(message);
        return this;
    }


}
