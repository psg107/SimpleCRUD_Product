package com.example.simplecrud_product.controller;

import com.example.simplecrud_product.model.common.Pagination;
import com.example.simplecrud_product.model.common.ServiceResponse;
import com.example.simplecrud_product.model.drink.common.AddDrink;
import com.example.simplecrud_product.model.drink.common.Drink;
import com.example.simplecrud_product.model.drink.common.UpdateDrink;
import com.example.simplecrud_product.model.drink.service.add.AddDrinkRequest;
import com.example.simplecrud_product.model.drink.service.add.AddDrinkResponse;
import com.example.simplecrud_product.model.drink.service.delete.DeleteDrinkResponse;
import com.example.simplecrud_product.model.drink.service.detail.GetDrinkDetailResponse;
import com.example.simplecrud_product.model.drink.service.get.GetDrinkResponse;
import com.example.simplecrud_product.model.drink.service.update.UpdateDrinkRequest;
import com.example.simplecrud_product.model.drink.service.update.UpdateDrinkResponse;
import com.example.simplecrud_product.service.DrinkService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
    public ServiceResponse<GetDrinkResponse> getDrinks(@RequestParam(value = "page", defaultValue = "1", required = false) int page) {

        var pageSizeValue = environment.getProperty("pagination.page-size");
        var pageSize = Integer.parseInt(pageSizeValue);
        Pagination<Drink> pagingDrinks = this.drinkService.getDrinks(page, pageSize);

        var response = new ServiceResponse(HttpStatus.OK, pagingDrinks);

        return response;
    }

    /**
     * 등록
     * @param addDrinkRequest
     * @return
     */
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ServiceResponse<AddDrinkResponse> addDrink(@RequestBody AddDrinkRequest addDrinkRequest, HttpServletResponse response) {
        var userId = this.getAuthorizedUserIDFromRequestToken();
        addDrinkRequest.setRegMemberId(userId);

        var addDrink = new DozerBeanMapper().map(addDrinkRequest, AddDrink.class);

        var added = this.drinkService.addDrink(addDrink);
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
    public ServiceResponse<GetDrinkDetailResponse> detail(@PathVariable int id, HttpServletResponse response) {
        var drink = this.drinkService.getDrink(id);
        if (drink == null) {
            return new ServiceResponse(HttpStatus.NOT_FOUND);
        }

        return new ServiceResponse(HttpStatus.OK, drink);
    }

    /**
     * 수정
     * @param updateDrinkRequest
     * @return
     */
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public ServiceResponse<UpdateDrinkResponse> update(@RequestBody UpdateDrinkRequest updateDrinkRequest, HttpServletResponse response) {
        var userId = this.getAuthorizedUserIDFromRequestToken();
        updateDrinkRequest.setRegMemberId(userId);

        var updateDrink = new DozerBeanMapper().map(updateDrinkRequest, UpdateDrink.class);

        var updated = this.drinkService.updateDrink(updateDrink);
        if (updated == false) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ServiceResponse(HttpStatus.OK, updateDrink);
    }

    /**
     * 삭제
     */
    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public ServiceResponse<DeleteDrinkResponse> delete(@PathVariable int id, HttpServletResponse response) {
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
