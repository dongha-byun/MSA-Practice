package com.example.orderservice.dto;

import com.example.orderservice.domain.Order;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderDto implements Serializable {

    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;

    private LocalDateTime createdAt;

    public OrderDto(String productId, Integer quantity, Integer unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public static OrderDto of(Order order) {
        return new OrderDto(order.getProductId(), order.getQuantity(), order.getUnitPrice(), order.getTotalPrice(),
                order.getOrderId(), order.getUserId(), order.getCreatedAt());
    }

    public static Order to(OrderDto orderDto) {
        return new Order(orderDto.getProductId(), orderDto.getQuantity(), orderDto.getUnitPrice(),
                orderDto.getTotalPrice(), orderDto.getOrderId(), orderDto.getUserId());
    }
}
