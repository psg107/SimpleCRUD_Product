package com.example.simplecrud_product.service.external;

import com.example.simplecrud_product.define.EurekaVirtualHostNames;
import com.example.simplecrud_product.model.common.ServiceResponse;
import com.example.simplecrud_product.model.member.profile.get.GetMemberProfileRequest;
import com.example.simplecrud_product.model.member.profile.get.GetMemberProfileResponse;
import com.example.simplecrud_product.model.member.profile.search.SearchMemberProfilesRequest;
import com.example.simplecrud_product.model.member.profile.search.SearchMemberProfilesResponse;
import com.example.simplecrud_product.model.member.profile.shared.MemberProfile;
import com.example.simplecrud_product.util.RestServiceClient;
import com.netflix.discovery.EurekaClient;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Component
public class MemberService {

    private final EurekaClient eurekaClient;

    public MemberService(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    public MemberProfile getMemberProfile(int memberId) {

        var instanceInfo = eurekaClient.getNextServerFromEureka(EurekaVirtualHostNames.SIMPLE_CRUD_MEMBER, false);

        //url
        var serviceHost = instanceInfo.getHomePageUrl();
        var endpoint = "api/member";
        var query = "?memberId=" + memberId;
        var url = serviceHost + endpoint + query;

        //request
        var serviceResponse = new RestServiceClient<GetMemberProfileRequest>()
                .setUrl(url)
                .setHttpMethod(HttpMethod.GET)
                .request(GetMemberProfileResponse.class);

        //response
        var memberProfile = serviceResponse.getData();

        return memberProfile;
    }

    public List<MemberProfile> getMemberProfiles(List<Integer> memberIds) {

        var instanceInfo = eurekaClient.getNextServerFromEureka(EurekaVirtualHostNames.SIMPLE_CRUD_MEMBER, false);

        //url
        var serviceHost = instanceInfo.getHomePageUrl();
        var endpoint = "api/member/search";
        var query = "?memberIds=" + String.join(",", memberIds.stream().map(String::valueOf).collect(Collectors.toList()));
        var url = serviceHost + endpoint + query;

        //data (?????? ????????????, object -> query string ????????? ??? ????????? ?????? ??????)
        var data = new SearchMemberProfilesRequest();
        data.setMemberIds(memberIds);

        //request
        var serviceResponse = new RestServiceClient<SearchMemberProfilesRequest>()
                .setUrl(url)
                .setHttpMethod(HttpMethod.GET)
                .request(SearchMemberProfilesResponse.class);

        //response
        var memberProfiles = serviceResponse.getData();

        return memberProfiles;
    }
}
