package com.example.simplecrud_product.controller;

import com.example.simplecrud_product.model.common.Pagination;
import com.example.simplecrud_product.model.drink.AddDrink;
import com.example.simplecrud_product.model.drink.Drink;
import com.example.simplecrud_product.model.drink.UpdateDrink;
import com.example.simplecrud_product.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/drink")
public class DrinkController {

    private final DrinkService drinkService;
    private final Environment environment;

    @Autowired
    public DrinkController(DrinkService drinkService, Environment environment) {
        this.drinkService = drinkService;
        this.environment = environment;
    }

    /**
     * 조회
     * @return
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public Pagination<Drink> getDrinks(@RequestParam(value = "page", defaultValue = "1", required = false) int page) {

        var pageSizeValue = environment.getProperty("pagination.page-size");
        var pageSize = Integer.parseInt(pageSizeValue);
        Pagination<Drink> pagingDrinks = this.drinkService.getDrinks(page, pageSize);

        return pagingDrinks;
    }

    /**
     * 등록
     * @param drink
     * @return
     */
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity addDrink(AddDrink drink) {
        var userId = this.getAuthorizedUserIDFromRequestToken();
        drink.setRegMemberId(userId);

        var added = this.drinkService.addDrink(drink);
        if (added == false) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 상세보기
     * @param id
     * @return
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity detail(@PathVariable int id) {
        var drink = this.drinkService.getDrink(id);
        if (drink == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Drink>(drink, HttpStatus.OK);
    }

    /**
     * 수정
     * @param drink
     * @return
     */
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public ResponseEntity update(UpdateDrink drink) {
        var userId = this.getAuthorizedUserIDFromRequestToken();
        drink.setRegMemberId(userId);

        var updated = this.drinkService.updateDrink(drink);
        if (updated == false) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<UpdateDrink>(drink, HttpStatus.OK);
    }

    /**
     * 삭제
     */
    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable int id) {
        var deleted = this.drinkService.deleteDrink(id);
        if (deleted == false){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
    
    @SuppressWarnings("인증을 어떻게 처리하지?")
    private int getAuthorizedUserIDFromRequestToken() {
        return 1;
    }
}
