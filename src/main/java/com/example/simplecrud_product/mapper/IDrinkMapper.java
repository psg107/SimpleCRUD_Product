package com.example.simplecrud_product.mapper;

import com.example.simplecrud_product.model.drink.common.AddDrink;
import com.example.simplecrud_product.model.drink.common.Drink;
import com.example.simplecrud_product.model.drink.common.UpdateDrink;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * resources/mapper/mapper.xml 와 매핑되는 인터페이스 정의
 */
@Mapper
@Repository
public interface IDrinkMapper {

    /**
     * 음료 총 개수 가져오기
     * @return
     */
    int getDrinkCount();

    /**
     * 모든 음료 가져오기
     * @return
     */
    ArrayList<Drink> getAllDrinks();

    /**
     * 음료 가져오기 (페이징)
     * @param skip
     * @param take
     * @return
     */
    ArrayList<Drink> getDrinksWithPaging(int skip, int take);

    /**
     * 특정 음료 가져오기
     * @param drinkId
     * @return
     */
    Drink getDrink(int drinkId);

    /**
     * 음료 추가
     * @param drink
     * @return
     */
    boolean addDrink(AddDrink drink);

    /**
     * 음료 수정
     * @param drink
     * @return
     */
    boolean updateDrink(UpdateDrink drink);

    /**
     * 음료 삭제
     * @param drinkId
     * @return
     */
    boolean deleteDrink(int drinkId);
}
