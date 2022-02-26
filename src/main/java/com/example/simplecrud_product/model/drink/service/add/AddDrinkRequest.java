package com.example.simplecrud_product.model.drink.service.add;

import com.example.simplecrud_product.exception.InvalidRequestModelException;
import com.example.simplecrud_product.model.common.ValidatableRequestModel;
import lombok.Getter;
import lombok.Setter;

public class AddDrinkRequest extends ValidatableRequestModel {
    /**
     * 메뉴명
     */
    @Getter
    @Setter
    private String name;

    /**
     * 가격
     */
    @Getter
    @Setter
    private int price;

    @Override
    public void validate() throws InvalidRequestModelException {
        if (name.isBlank()){
            throw new InvalidRequestModelException("메뉴명이 입력되지 않았습니다.");
        }
        if (price <= 0) {
            throw new InvalidRequestModelException("가격이 잘못되었습니다.");
        }
    }
}
