package com.example.catalogservice.vo;

import com.example.catalogservice.domain.Catalog;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class ResponseCatalog {
    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;
    private LocalDateTime createdAt;

    public static ResponseCatalog of(Catalog catalog) {
        return new ResponseCatalog(catalog.getProductId(), catalog.getProductName(), catalog.getStock(),
                catalog.getUnitPrice(), catalog.getCreatedAt());
    }
}
