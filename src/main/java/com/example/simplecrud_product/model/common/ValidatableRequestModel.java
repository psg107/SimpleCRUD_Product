package com.example.simplecrud_product.model.common;

import com.example.simplecrud_product.exception.InvalidRequestModelException;

public abstract class ValidatableRequestModel {
    public abstract void validate() throws InvalidRequestModelException;
}
