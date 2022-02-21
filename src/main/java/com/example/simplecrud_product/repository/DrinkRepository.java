package com.example.simplecrud_product.repository;

import com.example.simplecrud_product.mapper.IDrinkMapper;
import com.example.simplecrud_product.model.drink.common.AddDrink;
import com.example.simplecrud_product.model.drink.common.Drink;
import com.example.simplecrud_product.model.drink.common.UpdateDrink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DrinkRepository {

    private final IDrinkMapper mapper;

    @Autowired
    public DrinkRepository(IDrinkMapper mapper) {
        this.mapper = mapper;
    }

    public int getDrinkCount() {
        var count = mapper.getDrinkCount();

        return count;
    }

    public ArrayList<Drink> getAllDrinks() {
        var drinks = mapper.getAllDrinks();

        return drinks;
    }

    public ArrayList<Drink> getDrinksWithPaging(int pageNumber, int pageSize) {
        var drinks = mapper.getDrinksWithPaging((pageNumber - 1) * pageSize, pageSize);

        return drinks;
    }

    public Drink getDrink(int drinkId) {
        var drink = mapper.getDrink(drinkId);

        return drink;
    }

    public boolean addDrinkInfo(AddDrink drink) {
        var added = mapper.addDrink(drink);

        return added;
    }

    public boolean updateDrink(UpdateDrink drink) {
        var updated = mapper.updateDrink(drink);

        return updated;
    }

    public boolean deleteDrink(int drinkId) {
        var deleted = mapper.deleteDrink(drinkId);

        return deleted;
    }
}
