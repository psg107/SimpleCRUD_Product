package com.example.simplecrud_product.model.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @param <T>
 */
public class ServiceResponse<T> {

    @Getter
    private int httpStatus;

    @Getter
    private String message;

    @Getter
    private T data;

    public ServiceResponse(HttpStatus httpStatus, T data) {
        this.httpStatus = httpStatus.value();
        this.message = null;
        this.data = data;
    }

    public ServiceResponse(HttpStatus httpStatus) {
        this.httpStatus = httpStatus.value();
        this.message = null;
        this.data = null;
    }

    public ServiceResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus.value();
        this.message = message;
        this.data = null;
    }

    public ServiceResponse(HttpStatus httpStatus, String message, T data) {
        this.httpStatus = httpStatus.value();
        this.message = message;
        this.data = data;
    }
}