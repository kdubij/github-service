package org.kdubij.githubclient.api.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponseDto {
    private int code;
    private ErrorCode type;
    private String message;


    public ErrorResponseDto(ErrorCode type, String message) {
        this.type = type;
        this.message = message;
        this.code = type.ordinal();
    }
}
