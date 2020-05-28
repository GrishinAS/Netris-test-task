package com.innteam.aggregator.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.innteam.aggregator.model.SourceData;
import com.innteam.aggregator.model.TokenData;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClient {
    private RestTemplate rest;
    private HttpHeaders headers;
    @Value("${mocky.address}")
    private String server = "http://www.mocky.io/v2/5c51b9dd3400003252129fb5";

    public RestClient() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
    }

    public InputStream getCameras() throws IOException {
        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
        ResponseEntity<Resource> responseEntity = rest.exchange(server, HttpMethod.GET, requestEntity, Resource.class);
        InputStream inputStream = responseEntity.getBody().getInputStream();
        JsonFactory f = new MappingJsonFactory();
        JsonParser jp = f.createParser(inputStream);
        return inputStream;
    }

    public TokenData getToken(String uri) {
        return rest.getForObject(uri, TokenData.class);
    }

    public SourceData getSource(String uri) {
        return rest.getForObject(uri, SourceData.class);
    }
}
