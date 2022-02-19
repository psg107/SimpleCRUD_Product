package com.example.simplecrud_product.service.external;

import com.example.simplecrud_product.define.EurekaVirtualHostNames;
import com.example.simplecrud_product.model.member.profile.get.GetMemberProfileRequest;
import com.example.simplecrud_product.model.member.profile.get.GetMemberProfileResponse;
import com.example.simplecrud_product.model.member.profile.search.SearchMemberProfilesRequest;
import com.example.simplecrud_product.model.member.profile.search.SearchMemberProfilesResponse;
import com.example.simplecrud_product.model.member.profile.shared.MemberProfile;
import com.example.simplecrud_product.util.RestServiceClient;
import com.netflix.discovery.EurekaClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

        var serviceHost = instanceInfo.getHomePageUrl();
        var endpoint = "member/" + memberId;

        //#warning 문자열 합치는거 확인 필요
        //endpoint
        var url = serviceHost + endpoint;

        //#warning 뭔가 사용법이 잘못된 것 같은데..
        ResponseEntity<GetMemberProfileResponse> responseEntity = new RestServiceClient<GetMemberProfileRequest>()
                .setUrl(url)
                .setHttpMethod(HttpMethod.GET)
                .request(GetMemberProfileResponse.class);

        var response = responseEntity.getBody();
        var memberProfile = response.getMemberProfile();

        return memberProfile;
    }

    public List<MemberProfile> getMemberProfiles(List<Integer> memberIds) {

        var instanceInfo = eurekaClient.getNextServerFromEureka(EurekaVirtualHostNames.SIMPLE_CRUD_MEMBER, false);

        var serviceHost = instanceInfo.getHomePageUrl();
        var endpoint = "api/member/search";
        var query = "?memberIds=" + String.join(",", memberIds.stream().map(String::valueOf).collect(Collectors.toList()));

        //#warning 문자열 합치는거 확인 필요
        //endpoint
        var url = serviceHost + endpoint + query;

        //data
        //#warning object -> query string 변환할 수 있도록 처리 필요
        var data = new SearchMemberProfilesRequest();
        data.setMemberIds(memberIds);

        //#warning 뭔가 사용법이 잘못된 것 같은데..
        ResponseEntity<SearchMemberProfilesResponse> responseEntity = new RestServiceClient<SearchMemberProfilesRequest>()
                .setUrl(url)
                .setHttpMethod(HttpMethod.GET)
                .request(SearchMemberProfilesResponse.class);

        var response = responseEntity.getBody();
        var memberProfiles = response.getMemberProfiles();

        return memberProfiles;
    }
}
