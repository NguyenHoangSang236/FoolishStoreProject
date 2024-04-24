package com.backend.core.usecase.util;

import com.backend.core.infrastructure.config.constants.GlobalDefaultStaticVariables;
import com.backend.core.usecase.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
public class NetworkUtils {
    @Autowired
    LoggingService loggingService;


    public ResponseEntity getGhnResponse(String url, Map<String, Object> body) {
        String requestId = UUID.randomUUID().toString();
        String requestUrl = GlobalDefaultStaticVariables.GHN_API_DOMAIN + url;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("ShopId", GlobalDefaultStaticVariables.GHN_SHOP_ID);
        headers.set("Token", GlobalDefaultStaticVariables.GHN_REQUEST_TOKEN);
        headers.set(GlobalDefaultStaticVariables.REQUEST_ID, requestId);

        HttpEntity httpEntity = body != null
                ? new HttpEntity(body, headers)
                : new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(
                requestUrl,
                body == null ? HttpMethod.GET : HttpMethod.POST,
                httpEntity,
                Map.class
        );

        loggingService.logThirdPartyRequestAndResponse(requestUrl, headers, body, response);

        return response;
    }
}
