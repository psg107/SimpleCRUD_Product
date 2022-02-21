package com.example.simplecrud_product.controller;

import com.example.simplecrud_product.model.common.Pagination;
import com.example.simplecrud_product.model.common.ServiceResponse;
import com.example.simplecrud_product.model.drink.AddDrink;
import com.example.simplecrud_product.model.drink.Drink;
import com.example.simplecrud_product.model.drink.UpdateDrink;
import com.example.simplecrud_product.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ServiceResponse<Pagination<Drink>> getDrinks(@RequestParam(value = "page", defaultValue = "1", required = false) int page) {

        var pageSizeValue = environment.getProperty("pagination.page-size");
        var pageSize = Integer.parseInt(pageSizeValue);
        Pagination<Drink> pagingDrinks = this.drinkService.getDrinks(page, pageSize);

        var response = new ServiceResponse(HttpStatus.OK, pagingDrinks);

        return response;
    }

    /**
     * 등록
     * @param drink
     * @return
     */
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ServiceResponse addDrink(AddDrink drink) {
        var userId = this.getAuthorizedUserIDFromRequestToken();
        drink.setRegMemberId(userId);

        var added = this.drinkService.addDrink(drink);
        if (added == false) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ServiceResponse(HttpStatus.CREATED);
    }

    /**
     * 상세보기
     * @param id
     * @return
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ServiceResponse<Drink> detail(@PathVariable int id) {
        var drink = this.drinkService.getDrink(id);
        if (drink == null) {
            return new ServiceResponse(HttpStatus.NOT_FOUND);
        }

        return new ServiceResponse(HttpStatus.OK, drink);
    }

    /**
     * 수정
     * @param drink
     * @return
     */
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public ServiceResponse<UpdateDrink> update(UpdateDrink drink) {
        var userId = this.getAuthorizedUserIDFromRequestToken();
        drink.setRegMemberId(userId);

        var updated = this.drinkService.updateDrink(drink);
        if (updated == false) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ServiceResponse(HttpStatus.OK, drink);
    }

    /**
     * 삭제
     */
    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public ServiceResponse delete(@PathVariable int id) {
        var deleted = this.drinkService.deleteDrink(id);
        if (deleted == false){
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ServiceResponse(HttpStatus.OK);
    }
    
    @SuppressWarnings("인증을 어떻게 처리하지?")
    private int getAuthorizedUserIDFromRequestToken() {
        return 1;
    }
}
