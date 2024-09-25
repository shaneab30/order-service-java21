package com.service.order_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public List<OrdersResponse> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Optional<OrdersResponse> getOrderById(@PathVariable Long id){
        return orderService.getOrderById(id);
    }

    // @PostMapping
    // public Orders saveOrder(@RequestBody Orders orders){
    //     return orderService.saveOrder(orders);
    // }

    @PostMapping
    public OrdersResponse createOrders(@RequestBody Orders orders){
        return orderService.createOrders(orders);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
    }

    @GetMapping("/find/status/{status}")
    public List<OrdersResponse> getAllOrderStatus(@PathVariable String status){
        return orderService.getAllOrderStatus(status);
    }

    // @GetMapping("/find/item/{item}")
    // public List<OrdersResponse> getAllOrderItem(@PathVariable String item){
    //     return orderService.getAllOrderItem(item);
    // }
}
