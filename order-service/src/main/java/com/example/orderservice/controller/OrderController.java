package com.example.orderservice.controller;

import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/order-service")
@RestController
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> findOrderByUserId(@PathVariable("userId") String userId) {
        List<Order> orders = orderService.getAllOrdersByUser(userId);
        List<ResponseOrder> responseOrders = orders.stream().map(ResponseOrder::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseOrders);
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody RequestOrder requestOrder) {
        OrderDto orderDto = RequestOrder.to(requestOrder);
        orderDto.setUserId(userId);

        OrderDto order = orderService.createOrder(orderDto);
        ResponseOrder responseOrder = ResponseOrder.of(order);

        return ResponseEntity.ok(responseOrder);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<ResponseOrder> findOrderByOrderId(@PathVariable("orderId") String orderId) {
        OrderDto order = orderService.getOrderByOrderId(orderId);
        ResponseOrder responseOrder = ResponseOrder.of(order);

        return ResponseEntity.ok(responseOrder);
    }
}
