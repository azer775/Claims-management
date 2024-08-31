package org.example.gestionsinistre.handler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter

public enum BusinessErrorCode {
    NO_CODE(2,"no code",HttpStatus.NOT_IMPLEMENTED)
    ;
    private int code;
    private String description;
    private final HttpStatus httpStatus;

    BusinessErrorCode(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
