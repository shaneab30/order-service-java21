package com.service.order_service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrdersResponse {
    private Long id;
    private Product product;
    private Integer quantity;
    private BigDecimal price;
}
