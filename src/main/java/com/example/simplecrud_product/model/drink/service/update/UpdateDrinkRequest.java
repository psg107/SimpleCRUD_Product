package com.example.simplecrud_product.model.drink.service.update;

import com.example.simplecrud_product.exception.InvalidRequestModelException;
import com.example.simplecrud_product.model.common.ValidatableRequestModel;
import com.example.simplecrud_product.model.drink.common.UpdateDrink;
import lombok.Getter;
import lombok.Setter;

public class UpdateDrinkRequest extends ValidatableRequestModel {
    /**
     * id
     */
    @Getter
    @Setter
    private int drinkId;

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
        if (this.drinkId <= 0){
            throw new InvalidRequestModelException("아이디가 잘못되었습니다.");
        }
        if (this.name.isBlank()) {
            throw new InvalidRequestModelException("음료명이 입력되지 않았습니다.");
        }
        if (this.price <= 0) {
            throw new InvalidRequestModelException("가격이 잘못되었습니다.");
        }
    }
}
