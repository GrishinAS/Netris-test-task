package com.innteam.aggregator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenData {
    private String value;
    private int ttl;
}
