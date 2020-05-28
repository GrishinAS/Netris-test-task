package com.innteam.aggregator.utils;

import com.innteam.aggregator.model.Camera;
import com.innteam.aggregator.model.SourceData;
import com.innteam.aggregator.model.TokenData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RestClient {
    private RestTemplate rest;
    private HttpHeaders headers;
    @Value("${mocky.address}")
    private String server;

    public RestClient() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
    }

    public List<Camera> getCameras() {
        ResponseEntity<Camera[]> response = rest.getForEntity(server, Camera[].class);
        Camera[] body = response.getBody();
        return Arrays.asList(body);
    }

    public TokenData getToken(String uri) {
        return rest.getForObject(uri, TokenData.class);
    }

    public SourceData getSource(String uri) {
        return rest.getForObject(uri, SourceData.class);
    }
}
