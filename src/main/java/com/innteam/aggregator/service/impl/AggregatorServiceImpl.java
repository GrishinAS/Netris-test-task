package com.innteam.aggregator.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innteam.aggregator.model.AggregatedCamera;
import com.innteam.aggregator.model.Camera;
import com.innteam.aggregator.service.AggregatorService;
import com.innteam.aggregator.utils.RestClient;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class AggregatorServiceImpl implements AggregatorService {
    private final RestClient restClient;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public AggregatorServiceImpl(RestClient restClient) { //TODO IS IT SINGLETON?? HOW REQUEST WILL WORK ASYNC?
        this.restClient = restClient;
    }

    @Override
    public List<AggregatedCamera> getAggregatedData() {
        List<AggregatedCamera> aggregatedCameras = new ArrayList<>();
        //getList
        //put sources and tokens to queues
        JsonFactory f = new MappingJsonFactory();
        try {
            InputStream cameras = restClient.getCameras();
            JsonParser jp = f.createParser(cameras);
            JsonToken current = jp.nextToken();
            if (current != JsonToken.START_ARRAY) {
                throw new IllegalAccessException("Error: root should be object: quiting.");
            }
            while (jp.nextToken() != JsonToken.END_ARRAY) {
                JsonNode node = jp.readValueAsTree();
                current = jp.nextToken();
                Camera camera = mapper.readValue(node.toString(), Camera.class);
                aggregatedCameras.add(AggregatedCamera.builder()
                        .id(camera.getId())
                        .videoUrl(camera.getSourceDataUrl())
                        .urlType(camera.getTokenDataUrl())
                        .build());
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return aggregatedCameras;
    }
}
