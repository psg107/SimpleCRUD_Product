package com.example.simplecrud_product.controller;

import com.example.simplecrud_product.exception.InvalidRequestModelException;
import com.example.simplecrud_product.model.common.Pagination;
import com.example.simplecrud_product.model.common.ServiceResponse;
import com.example.simplecrud_product.model.drink.common.AddDrink;
import com.example.simplecrud_product.model.drink.common.Drink;
import com.example.simplecrud_product.model.drink.common.UpdateDrink;
import com.example.simplecrud_product.model.drink.service.add.AddDrinkRequest;
import com.example.simplecrud_product.model.drink.service.add.AddDrinkResponse;
import com.example.simplecrud_product.model.drink.service.delete.DeleteDrinkRequest;
import com.example.simplecrud_product.model.drink.service.delete.DeleteDrinkResponse;
import com.example.simplecrud_product.model.drink.service.detail.GetDrinkDetailRequest;
import com.example.simplecrud_product.model.drink.service.detail.GetDrinkDetailResponse;
import com.example.simplecrud_product.model.drink.service.get.GetDrinkRequest;
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

    @SuppressWarnings("인증을 어떻게 처리하지?")
    private int getAuthorizedUserIDFromRequestToken() {
        return 1;
    }

    /**
     * 조회
     * @return
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ServiceResponse<GetDrinkResponse> getDrinks(GetDrinkRequest request) throws Exception {
        //validate
        request.validate();

        //model parameter
        var page = request.getPage();

        //service
        var pageSizeValue = environment.getProperty("pagination.page-size");
        var pageSize = Integer.parseInt(pageSizeValue);
        Pagination<Drink> drinks = this.drinkService.getDrinks(page, pageSize);

        //return
        return new ServiceResponse(HttpStatus.OK, drinks);
    }

    /**
     * 등록
     * @param request
     * @return
     */
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ServiceResponse<AddDrinkResponse> addDrink(@RequestBody AddDrinkRequest request) throws Exception {
        //validate
        request.validate();

        //model mapping
        var userId = this.getAuthorizedUserIDFromRequestToken();
        var addDrink = new DozerBeanMapper().map(request, AddDrink.class);
        addDrink.setRegMemberId(userId);

        //service
        var added = this.drinkService.addDrink(addDrink);
        if (added == false) {
            throw new Exception("음료 등록 중 오류가 발생했습니다.");
        }

        //return
        return new ServiceResponse(HttpStatus.CREATED);
    }

    /**
     * 상세보기
     * @param request
     * @return
     */
    @RequestMapping(path = "/info", method = RequestMethod.GET)
    public ServiceResponse<GetDrinkDetailResponse> detail(GetDrinkDetailRequest request) throws Exception {
        //validate
        request.validate();

        //model parameter
        var drinkId = request.getDrinkId();

        //service
        var drink = this.drinkService.getDrink(drinkId);
        if (drink == null) {
            //#warning addDrink와 패턴이 다른데.. 모든 StatusCode마다 Exception을 선언해야하나?
            return new ServiceResponse(HttpStatus.NOT_FOUND);
        }

        //return
        return new ServiceResponse(HttpStatus.OK, drink);
    }

    /**
     * 수정
     * @param updateDrinkRequest
     * @return
     */
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public ServiceResponse<UpdateDrinkResponse> update(@RequestBody UpdateDrinkRequest updateDrinkRequest) throws Exception {
        //validate
        updateDrinkRequest.validate();

        //model mapping
        var userId = this.getAuthorizedUserIDFromRequestToken();
        var updateDrink = new DozerBeanMapper().map(updateDrinkRequest, UpdateDrink.class);
        updateDrink.setRegMemberId(userId);

        //service
        var updated = this.drinkService.updateDrink(updateDrink);
        if (updated == false) {
            throw new Exception("음료 수정 중 오류가 발생했습니다.");
        }

        //return
        return new ServiceResponse(HttpStatus.OK, updateDrink);
    }

    /**
     * 삭제
     */
    @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
    public ServiceResponse<DeleteDrinkResponse> delete(DeleteDrinkRequest request) throws Exception {
        //validate
        request.validate();

        //model parameter
        var drinkId = request.getDrinkId();

        //service
        var deleted = this.drinkService.deleteDrink(drinkId);
        if (deleted == false) {
            throw new Exception("음료 삭제 중 오류가 발생했습니다.");
        }

        //return
        return new ServiceResponse(HttpStatus.OK);
    }
}
