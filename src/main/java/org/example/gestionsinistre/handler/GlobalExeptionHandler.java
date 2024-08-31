package org.example.gestionsinistre.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.example.gestionsinistre.handler.BusinessErrorCode.NO_CODE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class GlobalExeptionHandler {
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExeptionResponse> handleExeption(LockedException exception){
        return ResponseEntity.status(UNAUTHORIZED).body(
                ExeptionResponse.builder().businessErrorCode(NO_CODE.getCode())
                        .businessExeptionDesc(NO_CODE.getDescription())
                        .error(exception.getMessage())
                        .build()
        );
    }
}
