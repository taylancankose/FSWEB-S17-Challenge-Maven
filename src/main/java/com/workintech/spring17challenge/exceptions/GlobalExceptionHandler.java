package com.workintech.spring17challenge.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiResponseError> handleException(ApiException apiException){
        ApiResponseError errorResponse = new ApiResponseError(
                apiException.getStatus().value(),
                apiException.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, apiException.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponseError> handleException(Exception exception){
        ApiResponseError errorResponse = new ApiResponseError(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
