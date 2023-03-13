package com.example.orderservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class Payload {
    private final String order_id;
    private final String user_id;
    private final String product_id;
    private final int quantity;
    private final int total_price;
    private final int unit_price;
}
