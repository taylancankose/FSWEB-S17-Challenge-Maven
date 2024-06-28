package com.workintech.spring17challenge.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiException extends RuntimeException{
    private HttpStatus status;

    public ApiException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
