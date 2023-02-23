package com.example.orderservice.vo;

import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class ResponseOrder {

    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createdAt;
    private String orderId;

    public static ResponseOrder of(Order order) {
        return new ResponseOrder(order.getOrderId(), order.getQuantity(), order.getUnitPrice(), order.getTotalPrice(), order.getCreatedAt(), order.getOrderId());
    }

    public static ResponseOrder of(OrderDto dto) {
        return new ResponseOrder(dto.getProductId(), dto.getQuantity(), dto.getUnitPrice(), dto.getTotalPrice(), dto.getCreatedAt(), dto.getOrderId());
    }
}
