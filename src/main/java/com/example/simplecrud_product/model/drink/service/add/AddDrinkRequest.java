package com.example.simplecrud_product.model.drink.service.add;

import lombok.Getter;
import lombok.Setter;

public class AddDrinkRequest {
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

    /**
     * 음료 등록한 사용자 ID
     */
    @Getter
    @Setter
    private int regMemberId;
}
