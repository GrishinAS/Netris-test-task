package com.innteam.aggregator.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.innteam.aggregator.model.AggregatedCamera;
import com.innteam.aggregator.utils.RestClient;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mock;

public class AggregatorServiceImplTest {
    private AggregatorServiceImpl aggregatorService;

    //@Mock
    private RestClient restClient;// = mock(RestClient.class);

    @Before
    public void init(){
        restClient = new RestClient();
        aggregatorService = new AggregatorServiceImpl(restClient);
    }

    @org.junit.Test
    public void getAggregatedData() throws IOException {
        URL resource = getClass().getClassLoader().getResource("static/example.json");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(resource.openConnection().getInputStream());
        //when(restClient.getCameras()).thenReturn(bufferedInputStream);
        List<AggregatedCamera> aggregatedData = aggregatorService.getAggregatedData();
        Assert.assertTrue(aggregatedData.size() > 0);
    }
}