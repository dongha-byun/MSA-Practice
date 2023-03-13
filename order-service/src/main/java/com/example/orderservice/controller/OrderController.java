package com.example.orderservice.controller;

import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.message.KafkaOrderProducer;
import com.example.orderservice.message.KafkaProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/order-service")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;
    private final KafkaOrderProducer orderProducer;

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
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getUnitPrice() * orderDto.getQuantity());

        OrderDto order = orderService.createOrder(orderDto);
        ResponseOrder responseOrder = ResponseOrder.of(order);

        /* send data from kafka */
        kafkaProducer.send("example-catalog-topic", orderDto);
        orderProducer.send("order-topic", orderDto);

        return ResponseEntity.created(URI.create("/"+userId+"/orders/"+responseOrder.getOrderId())).body(responseOrder);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<ResponseOrder> findOrderByOrderId(@PathVariable("orderId") String orderId) {
        OrderDto order = orderService.getOrderByOrderId(orderId);
        ResponseOrder responseOrder = ResponseOrder.of(order);

        return ResponseEntity.ok(responseOrder);
    }
}
