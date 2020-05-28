package com.innteam.aggregator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AggregatedCamera {
    private int id;
    private String urlType;
    private String videoUrl;
    private String value;
    private int ttl;

  public AggregatedCamera(int id) {
    this.id = id;
  }
}
