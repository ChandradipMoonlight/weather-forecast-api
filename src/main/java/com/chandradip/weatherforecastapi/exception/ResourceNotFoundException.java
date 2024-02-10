package com.chandradip.weatherforecastapi.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException{
    private Integer statusCode;
    private String fieldName;
    private String fieldValue;
    private String resourceName;


    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s Not Found With %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
