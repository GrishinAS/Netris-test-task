package com.innteam.aggregator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SourceData {
    private String urlType;
    private String videoUrl;
}
