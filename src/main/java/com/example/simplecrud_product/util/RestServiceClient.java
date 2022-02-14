package com.example.simplecrud_product.util;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestServiceClient<T> {

    private String url;
    private HttpMethod method;
    private T data;
    private MultiValueMap<String, String> headers;

    public RestServiceClient() {
        this.headers = new LinkedMultiValueMap<String, String>();
    }

    public RestServiceClient setUrl(String url) {
        this.url = url;
        return this;
    }

    public RestServiceClient setHttpMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public RestServiceClient setData(T data) {
        this.data = data;
        return this;
    }

    public RestServiceClient addHeader(String key, String value) {
        this.headers.add(key, value);
        return this;
    }

    public <T2> ResponseEntity<T2> request(Class<T2> responseType) {
        
        //template 생성
        var restTemplate = new RestTemplate();

        //header 설정
        var headers = new HttpHeaders();
        headers.addAll(this.headers);

        //body 설정
        HttpEntity<T> requestEntity = switch (this.method) {
            case GET -> new HttpEntity<T>(headers);
            case POST -> new HttpEntity<T>(this.data, headers);

            default -> throw new NotImplementedException();
        };

        //request
        var responseEntity = restTemplate.exchange(this.url, this.method, requestEntity, responseType);

        return responseEntity;
    }
}
