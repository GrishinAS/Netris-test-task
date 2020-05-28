package com.innteam.aggregator.service.impl;

import com.innteam.aggregator.model.AggregatedCamera;
import com.innteam.aggregator.model.Camera;
import com.innteam.aggregator.model.SourceData;
import com.innteam.aggregator.model.TokenData;
import com.innteam.aggregator.service.AggregatorService;
import com.innteam.aggregator.utils.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AggregatorServiceImpl implements AggregatorService {

  private final RestClient restClient;
  private ExecutorService dataCollectors = Executors.newCachedThreadPool();

  @Autowired
  public AggregatorServiceImpl(RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public List<AggregatedCamera> getAggregatedData() {
    Map<Integer, AggregatedCamera> aggregatedCamerasMap = new ConcurrentHashMap<>();
    try {
      List<Camera> cameras = restClient.getCameras();
        CountDownLatch latch = new CountDownLatch(cameras.size() * 2);
      for (Camera camera : cameras) {
        aggregatedCamerasMap.put(camera.getId(), new AggregatedCamera(camera.getId()));
        getSourceData(camera, aggregatedCamerasMap, latch);
        getTokenData(camera, aggregatedCamerasMap, latch);
      }
        latch.await();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
      return new ArrayList<>(aggregatedCamerasMap.values());
  }

  private void getTokenData(Camera camera, Map<Integer, AggregatedCamera> aggregatedCamerasMap, CountDownLatch latch) {
    dataCollectors.execute(() -> {
      try {
        SourceData source = restClient.getSource(camera.getSourceDataUrl());
        AggregatedCamera aggregatedCamera = aggregatedCamerasMap.get(camera.getId());
        aggregatedCamera.setVideoUrl(source.getVideoUrl());
        aggregatedCamera.setUrlType(source.getUrlType());
        aggregatedCamerasMap.put(camera.getId(), aggregatedCamera);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        latch.countDown();
      }

    });
  }

  private void getSourceData(Camera camera, Map<Integer, AggregatedCamera> aggregatedCamerasMap, CountDownLatch latch) {
    dataCollectors.execute(() -> {
      try {
        TokenData source = restClient.getToken(camera.getTokenDataUrl());
        AggregatedCamera aggregatedCamera = aggregatedCamerasMap.get(camera.getId());
        aggregatedCamera.setTtl(source.getTtl());
        aggregatedCamera.setValue(source.getValue());
        aggregatedCamerasMap.put(camera.getId(), aggregatedCamera);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        latch.countDown();
      }

    });
  }
}
