package com.example.orderservice.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderId(String orderId);
    List<Order> findAllByUserId(String userId);
}
