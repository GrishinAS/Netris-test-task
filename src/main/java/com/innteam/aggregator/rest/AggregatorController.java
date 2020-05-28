package com.innteam.aggregator.rest;

import com.innteam.aggregator.model.AggregatedCamera;
import com.innteam.aggregator.service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AggregatorController {

    private final AggregatorService aggregatorService;

    @Autowired
    public AggregatorController(AggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @GetMapping("/getAggregatedData")
    public List<AggregatedCamera> getAggregatedData() {
        return aggregatorService.getAggregatedData();
    }
}
