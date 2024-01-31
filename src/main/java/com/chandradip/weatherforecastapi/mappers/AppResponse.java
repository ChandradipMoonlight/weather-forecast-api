package com.chandradip.weatherforecastapi.mappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppResponse {
    private String status;
    private Object data;
    private int statusCode;

    public AppResponse(Object data, String status) {
        this.data = data;
        this.status = status;
    }
    public AppResponse(Object data, String status, int statusCode) {
        this.data = data;
        this.status = status;
        this.statusCode = statusCode;
    }
}
