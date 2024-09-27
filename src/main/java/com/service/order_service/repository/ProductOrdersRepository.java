package com.service.order_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.service.order_service.model.ProductOrders;
public interface ProductOrdersRepository extends JpaRepository<ProductOrders, Long> {
    
}
