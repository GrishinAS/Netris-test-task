package com.innteam.aggregator.service;

import com.innteam.aggregator.model.AggregatedCamera;

import java.util.List;

public interface AggregatorService {
    List<AggregatedCamera> getAggregatedData();
}
