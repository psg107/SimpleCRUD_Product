package com.example.simplecrud_product.model.drink.service.get;

import com.example.simplecrud_product.exception.InvalidRequestModelException;
import com.example.simplecrud_product.model.common.ValidatableRequestModel;
import lombok.Getter;
import lombok.Setter;

public class GetDrinkRequest extends ValidatableRequestModel {
    @Getter
    @Setter
    private int page;

    @Override
    public void validate() throws InvalidRequestModelException {
        if (page <= 0) {
            throw new InvalidRequestModelException("페이지 번호가 잘못되었습니다.");
        }
    }
}
