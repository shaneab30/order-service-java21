package com.service.order_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.order_service.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Orders findByCustomerId(Long customerId);

    // List<Orders> findByItem(String item);

    List<Orders> findAllByStatus(String status);
}
