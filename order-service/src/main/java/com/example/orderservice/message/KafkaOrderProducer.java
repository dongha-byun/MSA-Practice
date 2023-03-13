package com.example.orderservice.message;

import com.example.orderservice.dto.Field;
import com.example.orderservice.dto.KafkaOrderDto;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.Payload;
import com.example.orderservice.dto.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaOrderProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, OrderDto orderDto) {
        try {
            KafkaOrderDto kafkaOrderDto = convertToKafkaOrderDto(orderDto);
            ObjectMapper mapper = new ObjectMapper();

            String jsonString = mapper.writeValueAsString(kafkaOrderDto);

            kafkaTemplate.send(topic, jsonString);
            log.info("send order dto to another order micro service = {}", jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private KafkaOrderDto convertToKafkaOrderDto(OrderDto orderDto) {

        List<Field> fields = Arrays.asList(
                new Field("string", true, "order_id"),
                new Field("string", true, "user_id"),
                new Field("string", true, "product_id"),
                new Field("int32", true, "quantity"),
                new Field("int32", true, "unit_price"),
                new Field("int32", true, "total_price")
        );

        Schema schema = Schema.builder()
                .type("struct")
                .fields(fields)
                .optional(true)
                .name("orders")
                .build();

        Payload payload = Payload.builder()
                .order_id(orderDto.getOrderId())
                .user_id(orderDto.getUserId())
                .product_id(orderDto.getProductId())
                .quantity(orderDto.getQuantity())
                .unit_price(orderDto.getUnitPrice())
                .total_price(orderDto.getTotalPrice())
                .build();

        return new KafkaOrderDto(schema, payload);
    }
}
