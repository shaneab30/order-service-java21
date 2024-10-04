package com.service.order_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.order_service.dto.OrdersResponse;
import com.service.order_service.model.Orders;
import com.service.order_service.service.OrdersService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService orderService;

    @GetMapping
    public List<OrdersResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    // @GetMapping("/{id}")
    // public Optional<OrdersResponse> getOrderById(@PathVariable Long id) {
    //     return orderService.getOrderById(id);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrderById(@PathVariable Long id) {
        try {
            orderService.getOrderById(id);
            return ResponseEntity.ok("Order retrieved successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @PostMapping
    public ResponseEntity<String> createOrders(@RequestBody Orders orders) {
        try {
            orderService.createOrders(orders);
            return ResponseEntity.ok("Order created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrders(@PathVariable Long id, @RequestBody Orders orders) {
        try {
            orderService.updateOrders(orders);
            return ResponseEntity.ok("Order updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Order deleted successfully");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/find/status/{status}")
    public List<OrdersResponse> getAllOrderStatus(@PathVariable String status) {
        return orderService.getAllOrderStatus(status);
    }

    // @GetMapping("/find/item/{item}")
    // public List<OrdersResponse> getAllOrderItem(@PathVariable String item){
    // return orderService.getAllOrderItem(item);
    // }
}
