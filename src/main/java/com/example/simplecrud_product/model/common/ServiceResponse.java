package com.example.simplecrud_product.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @param <T>
 */
public class ServiceResponse<T> {

    @Getter
    private int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    private T data;

    public ServiceResponse() {

    }

    public ServiceResponse(HttpStatus httpStatus, T data) {
        this.code = httpStatus.value();
        this.message = null;
        this.data = data;
    }

    public ServiceResponse(HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.message = null;
        this.data = null;
    }

    public ServiceResponse(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.message = message;
        this.data = null;
    }

    public ServiceResponse(HttpStatus httpStatus, String message, T data) {
        this.code = httpStatus.value();
        this.message = message;
        this.data = data;
    }
}