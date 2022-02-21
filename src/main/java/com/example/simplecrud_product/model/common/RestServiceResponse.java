package com.example.simplecrud_product.model.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * <seealso>RestServiceClient.java??</seealso>
 * @param <T>
 */
public class RestServiceResponse<T> {

    @Getter
    @Setter
    private HttpStatus statusCode;

    @Getter
    @Setter
    private T data;
}
