package com.chandradip.weatherforecastapi.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserAlreadyPresent extends RuntimeException{

    private Integer statusCode;

    public UserAlreadyPresent(String message, Integer statusCode) {
        super(message);
        this.statusCode =statusCode;
    }
}
