package com.example.simplecrud_product.model.drink.common;

import lombok.Getter;
import lombok.Setter;

/**
 * 음료 정보
 */
public class Drink {
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

    /**
     * 음료 등록한 사용자 ID
     */
    @Getter
    @Setter
    private int regMemberId;

    /**
     * 음료 등록한 사용자명
     */
    @Getter
    @Setter
    private String regMemberName;
}
