package com.chandradip.weatherforecastapi.mappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
