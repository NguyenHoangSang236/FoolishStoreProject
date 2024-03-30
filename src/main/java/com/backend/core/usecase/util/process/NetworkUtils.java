package com.backend.core.usecase.util.process;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class NetworkUtils {
    public ResponseEntity getGhnPostResponse(String url, Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("ShopId", "190298");
        headers.set("Token", "10a16ebf-7fa0-11ee-8bfa-8a2dda8ec551");

        return getPostResponse("https://dev-online-gateway.ghn.vn/shiip/public-api" + url, headers, body);
    }

    public ResponseEntity getGhnGetResponse(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("ShopId", "190298");
        headers.set("Token", "10a16ebf-7fa0-11ee-8bfa-8a2dda8ec551");

        return getGetResponse("https://dev-online-gateway.ghn.vn/shiip/public-api" + url, headers);
    }

    public ResponseEntity<Map> getPostResponse(String url, HttpHeaders header, Map<String, Object> body) {
        HttpEntity httpEntity = new HttpEntity(body, header);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
    }

    public ResponseEntity<Map> getGetResponse(String url, HttpHeaders header) {
        HttpEntity httpEntity = new HttpEntity(header);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Map.class);
    }
}
