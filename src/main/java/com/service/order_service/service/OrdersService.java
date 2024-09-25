package com.service.order_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.order_service.dto.Customer;
import com.service.order_service.dto.OrdersResponse;
import com.service.order_service.dto.Product;
import com.service.order_service.dto.ProductOrdersResponse;
import com.service.order_service.model.Orders;
import com.service.order_service.model.ProductOrders;
import com.service.order_service.repository.OrdersRepository;

import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository orderRepository;

    @Value("${customer.api.url}")
    private String customerApiUrl;

    @Value("${product.api.url}")
    private String productApiUrl;

    public List<OrdersResponse> getAllOrders() {
        List<Orders> orders = orderRepository.findAll();
        return orders.stream().map(o -> {

            Customer customerResponse = getCustomerDetails(o.getCustomerId());
            System.out.println("Retrieved customer: " + customerResponse);
            Customer customer = new Customer();
            customer.setId(customerResponse.getId());
            customer.setFirstname(customerResponse.getFirstname());
            customer.setLastname(customerResponse.getLastname());
            customer.setEmail(customerResponse.getEmail());
            customer.setAddress(customerResponse.getAddress());
            customer.setTelephone(customerResponse.getTelephone());
            customer.setBirthday(customerResponse.getBirthday());

            List<ProductOrdersResponse> productOrders = o.getProductOrders().stream().map(productOrder -> {
                Product productResponse = getProductDetails(productOrder.getProductId());
                return new ProductOrdersResponse(productOrder.getId(), productResponse, productOrder.getQuantity(),
                        productOrder.getPrice());
            }).collect(Collectors.toList());

            return convertToOrderResponse(o, customer, productOrders);
        }).toList();
        // use .collect(Collectors.toList()) for mutable list
        // return
        // orders.stream().map(this::convertToOrderResponse).collect(Collectors.toList());
    }

    public Optional<OrdersResponse> getOrderById(Long id) {
        Optional<Orders> order = orderRepository.findById(id);
        return order.map(o -> {
            
            Customer customerResponse = getCustomerDetails(o.getCustomerId());
            System.out.println("Retrieved customer: " + customerResponse);

            Customer customer = new Customer();
            customer.setId(customerResponse.getId());
            customer.setFirstname(customerResponse.getFirstname());
            customer.setLastname(customerResponse.getLastname());
            customer.setEmail(customerResponse.getEmail());
            customer.setAddress(customerResponse.getAddress());
            customer.setTelephone(customerResponse.getTelephone());
            customer.setBirthday(customerResponse.getBirthday());

            List<ProductOrdersResponse> productOrders = o.getProductOrders().stream().map(productOrder -> {
                Product productResponse = getProductDetails(productOrder.getProductId());
                return new ProductOrdersResponse(productOrder.getId(), productResponse, productOrder.getQuantity(),
                        productOrder.getPrice());
            }).collect(Collectors.toList());

            return convertToOrderResponse(o, customer, productOrders);
        });
    }

    public OrdersResponse createOrders(Orders orders) {
        Customer customerResponse = getCustomerDetails(orders.getCustomerId());
        if (customerResponse == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Customer customer = new Customer();
        customer.setId(customerResponse.getId());
        customer.setFirstname(customerResponse.getFirstname());
        customer.setLastname(customerResponse.getLastname());
        customer.setEmail(customerResponse.getEmail());
        customer.setAddress(customerResponse.getAddress());
        customer.setTelephone(customerResponse.getTelephone());
        customer.setBirthday(customerResponse.getBirthday());
        System.out.println("Retrieved customer: " + customerResponse);

        List<ProductOrders> productOrders = orders.getProductOrders().stream().map(productOrder -> {
            Product productResponse = getProductDetails(productOrder.getProductId());
            if (productResponse == null) {
                throw new IllegalArgumentException("ID cannot be null");
            }
            ProductOrders newProductOrder = new ProductOrders();
            newProductOrder.setProductId(productOrder.getProductId());
            newProductOrder.setQuantity(productOrder.getQuantity());
            newProductOrder.setPrice(productOrder.getPrice());
            newProductOrder.setOrders(orders);

            return newProductOrder;
        }).toList();

        orders.setProductOrders(productOrders);
        orders.setShippingAddress(customerResponse.getAddress());

        List<ProductOrdersResponse> productOrdersResponse = productOrders.stream()
                .map(this::convertToProductOrdersResponse).toList();

        return convertToOrderResponse(saveOrder(orders), customer, productOrdersResponse);
    }

    public Orders saveOrder(Orders orders) {
        return orderRepository.save(orders);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<OrdersResponse> getAllOrderStatus(String status) {
        List<Orders> orders = orderRepository.findAllByStatus(status);
        return orders.stream().map(o -> {

            Customer customerResponse = getCustomerDetails(o.getCustomerId());
            System.out.println("Retrieved customer: " + customerResponse);
            Customer customer = new Customer();
            customer.setId(customerResponse.getId());
            customer.setFirstname(customerResponse.getFirstname());
            customer.setLastname(customerResponse.getLastname());
            customer.setEmail(customerResponse.getEmail());
            customer.setAddress(customerResponse.getAddress());
            customer.setTelephone(customerResponse.getTelephone());
            customer.setBirthday(customerResponse.getBirthday());

            List<ProductOrdersResponse> productOrders = o.getProductOrders().stream().map(productOrder -> {
                Product productResponse = getProductDetails(productOrder.getProductId());
                return new ProductOrdersResponse(productOrder.getId(), productResponse, productOrder.getQuantity(),
                        productOrder.getPrice());
            }).collect(Collectors.toList());

            return convertToOrderResponse(o, customer, productOrders);
        }).toList();
        // use .collect(Collectors.toList()) for mutable list
    }

    // Functions
    private OrdersResponse convertToOrderResponse(Orders order, Customer customer,
            List<ProductOrdersResponse> productOrders) {
        return new OrdersResponse(
                order.getId(),
                customer,
                order.getOrderDate(),
                order.getStatus(),
                order.getTotalPrice(),
                order.getShippingAddress(),
                productOrders);
    }

    private ProductOrdersResponse convertToProductOrdersResponse(ProductOrders productOrders) {
        return new ProductOrdersResponse(
                productOrders.getId(),
                getProductDetails(productOrders.getProductId()),
                productOrders.getQuantity(),
                productOrders.getPrice());
    }

    public Customer getCustomerDetails(Long customerId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = customerApiUrl + customerId;
        return restTemplate.getForObject(url, Customer.class);
    }

    public Product getProductDetails(Long productId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = productApiUrl + productId;
        return restTemplate.getForObject(url, Product.class);
    }
}
