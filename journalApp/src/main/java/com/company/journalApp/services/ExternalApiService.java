package com.company.journalApp.services;

import com.company.journalApp.api.dummy.DemoAPI;
import com.company.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {
    private static final String apiKey = "anykey-you-want233";
    private static final String API = "the-endpoint-of-api";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    RedisService redisService;

    public DemoAPI getAPIresponse(String city){
        DemoAPI apiResponse = redisService.get(city, DemoAPI.class);
        if(apiResponse != null){
            return apiResponse;
        }
        else {
            String apiTocall = appCache.cacheMap.get("weather_api");   // fetches from the app cache
            ResponseEntity<DemoAPI> response = restTemplate.exchange(apiTocall, HttpMethod.GET, null, DemoAPI.class);
            DemoAPI body = response.getBody();

            if(body != null)
                redisService.set(city, body, 300l);

            return body;
        }

    }
}
