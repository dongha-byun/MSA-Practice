package com.example.orderservice.service;

import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderRepository;
import com.example.orderservice.dto.OrderDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDetails) {
//        orderDetails.setOrderId(UUID.randomUUID().toString());
//        orderDetails.setTotalPrice(orderDetails.getUnitPrice() * orderDetails.getQuantity());

        Order order = orderRepository.save(OrderDto.to(orderDetails));

        return OrderDto.of(order);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        return OrderDto.of(order);
    }

    @Override
    public List<Order> getAllOrdersByUser(String userId) {
        return orderRepository.findAllByUserId(userId);
    }
}
