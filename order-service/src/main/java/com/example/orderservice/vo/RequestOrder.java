package com.example.orderservice.vo;

import com.example.orderservice.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RequestOrder {

    private String productId;
    private Integer quantity;
    private Integer unitPrice;

    public static OrderDto to(RequestOrder order) {
        return new OrderDto(order.getProductId(), order.getQuantity(), order.getUnitPrice());
    }
}
