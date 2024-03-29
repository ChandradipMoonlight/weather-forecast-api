package com.chandradip.weatherforecastapi.mappers;

import com.chandradip.weatherforecastapi.constants.AppConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppErrors {
    private String status;
    private Object errors;

    public AppErrors(Object errors) {
        this.errors = errors;
        this.status = AppConstants.FAIL;
    }
}
