package com.example.simplecrud_product.model.member.profile.search;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SearchMemberProfilesRequest {

    @Getter
    @Setter
    private List<Integer> memberIds;
}
