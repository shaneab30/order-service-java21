package com.service.order_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponse {
    private Long id;
    private Customer customer;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal totalPrice;
    private String shippingAddress;
    private List<ProductOrdersResponse> productOrders;
}
