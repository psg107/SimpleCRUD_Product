package com.example.simplecrud_product.model.drink.service.detail;

import com.example.simplecrud_product.exception.InvalidRequestModelException;
import com.example.simplecrud_product.model.common.ValidatableRequestModel;
import lombok.Getter;
import lombok.Setter;

public class GetDrinkDetailRequest extends ValidatableRequestModel {
    @Getter
    @Setter
    private int drinkId;

    @Override
    public void validate() throws InvalidRequestModelException {
        if (drinkId <= 0) {
            throw new InvalidRequestModelException("아이디가 잘못되었습니다.");
        }
    }
}
