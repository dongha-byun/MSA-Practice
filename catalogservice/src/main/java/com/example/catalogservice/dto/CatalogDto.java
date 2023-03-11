package com.example.catalogservice.dto;

import com.example.catalogservice.domain.Catalog;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CatalogDto implements Serializable {
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;

    public static CatalogDto of(Catalog catalog) {
        return new CatalogDto();
    }
}
