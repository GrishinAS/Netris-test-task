package com.innteam.aggregator.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AggregatedCamera {
    private int id;
    private String urlType;
    private String videoUrl;
    private String value;
    private int ttl;
}
