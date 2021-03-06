package com.innteam.aggregator;

import com.innteam.aggregator.model.AggregatedCamera;
import com.innteam.aggregator.service.impl.AggregatorServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class StartApplicationTests {

  @Autowired
  private AggregatorServiceImpl aggregatorService;

  @Test
  void getAggregatedData() {
    List<AggregatedCamera> aggregatedData = aggregatorService.getAggregatedData();
    Assert.assertTrue(aggregatedData.size() > 0);
  }

}
