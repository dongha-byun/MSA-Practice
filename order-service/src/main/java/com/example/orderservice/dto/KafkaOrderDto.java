package com.example.orderservice.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class KafkaOrderDto implements Serializable {
    private final Schema schema;
    private final Payload payload;
}
