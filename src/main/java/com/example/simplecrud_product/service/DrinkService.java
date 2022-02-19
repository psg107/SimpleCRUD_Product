package com.example.simplecrud_product.service;

import com.example.simplecrud_product.model.common.Pagination;
import com.example.simplecrud_product.model.drink.AddDrink;
import com.example.simplecrud_product.model.drink.Drink;
import com.example.simplecrud_product.model.drink.UpdateDrink;
import com.example.simplecrud_product.repository.DrinkRepository;
import com.example.simplecrud_product.service.external.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class DrinkService {

    private final DrinkRepository drinkRepository;
    private final MemberService memberService;

    @Autowired
    public DrinkService(DrinkRepository drinkInfoRepository, MemberService memberService) {
        this.drinkRepository = drinkInfoRepository;
        this.memberService = memberService;
    }

    /**
     * 모든 음료 정보 가져오기
     * @return
     */
    public ArrayList<Drink> getDrinks() {
        var drinks = this.drinkRepository.getAllDrinks();

        return drinks;
    }

    /**
     * 음료 정보 가져오기 (페이징)
     * @return
     */
    public Pagination<Drink> getDrinks(int pageNumber, int pageSize) {
        var itemCount = this.drinkRepository.getDrinkCount();
        var drinks = this.drinkRepository.getDrinksWithPaging(pageNumber, pageSize);

        var memberIds = drinks.stream()
                .map(x -> x.getRegMemberId())
                .distinct()
                .collect(Collectors.toList());
        var memberProfiles = this.memberService.getMemberProfiles(memberIds);

        drinks.forEach(drink -> {
            var memberProfile = memberProfiles.stream()
                    .filter(x -> x.getMemberId() == drink.getRegMemberId())
                    .findFirst()
                    .orElse(null);

            if (memberProfile != null) {
                var memberName = memberProfile.getName();
                drink.setRegMemberName(memberName);
            }
        });

        var pagingDrinks = new Pagination<Drink>(pageNumber, itemCount, drinks);

        return pagingDrinks;
    }

    /**
     * 특정 음료 정보 가져오기
     * @param drinkId
     * @return
     */
    public Drink getDrink(int drinkId) {
        var drink = this.drinkRepository.getDrink(drinkId);
        var memberId = drink.getRegMemberId();

        var memberProfile = this.memberService.getMemberProfile(memberId);
        var memberName = memberProfile != null ? memberProfile.getName() : "";

        drink.setRegMemberName(memberName);

        return drink;
    }

    /**
     * 음료 메뉴 추가
     * @param drink
     * @return
     */
    public boolean addDrink(AddDrink drink) {
        var added = this.drinkRepository.addDrinkInfo(drink);

        return added;
    }

    /**
     * 음료 정보 수정
     * @param drink
     * @return
     */
    public boolean updateDrink(UpdateDrink drink) {
        var updated = this.drinkRepository.updateDrink(drink);

        return updated;
    }

    /**
     * 음료 정보 삭제
     * @param drinkId
     * @return
     */
    public boolean deleteDrink(int drinkId) {
        var deleted = this.drinkRepository.deleteDrink(drinkId);

        return deleted;
    }
}
