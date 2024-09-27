package com.service.order_service.service;

import java.util.List;
import java.util.ArrayList;

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
import com.service.order_service.repository.ProductOrdersRepository;

import org.springframework.web.client.RestTemplate;

import java.util.Optional;
// import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository orderRepository;

    @Autowired
    private ProductOrdersRepository productOrdersRepository;

    @Value("${customer.api.url}")
    private String customerApiUrl;

    @Value("${product.api.url}")
    private String productApiUrl;

    public List<OrdersResponse> getAllOrders() {
        List<Orders> orders = orderRepository.findAll();

        List<OrdersResponse> ordersResponse = new ArrayList<>();

        for (Orders order : orders) {
            Customer customerResponse = getCustomerDetails(order.getCustomerId());
            if (customerResponse == null) {
                throw new IllegalArgumentException("Customer ID not found");
            }
            Customer customer = new Customer();
            customer.setId(customerResponse.getId());
            customer.setFirstname(customerResponse.getFirstname());
            customer.setLastname(customerResponse.getLastname());
            customer.setEmail(customerResponse.getEmail());
            customer.setAddress(customerResponse.getAddress());
            customer.setTelephone(customerResponse.getTelephone());
            customer.setBirthday(customerResponse.getBirthday());

            List<ProductOrdersResponse> productOrdersResponse = new ArrayList<>();

            for (ProductOrders productOrders : order.getProductOrders()) {
                Product productResponse = getProductDetails(productOrders.getProductId());

                if (productResponse == null) {
                    throw new IllegalArgumentException("Product ID not found");
                }
                ProductOrdersResponse newProductOrdersResponse = new ProductOrdersResponse();
                newProductOrdersResponse.setId(productOrders.getId());
                newProductOrdersResponse.setProduct(productResponse);
                newProductOrdersResponse.setQuantity(productOrders.getQuantity());
                newProductOrdersResponse.setPrice(productOrders.getPrice());
                productOrdersResponse.add(newProductOrdersResponse);
            }
            ordersResponse.add(convertToOrderResponse(order, customer, productOrdersResponse));
        }
        return ordersResponse;
    }

    public Optional<OrdersResponse> getOrderById(Long id) {
        Optional<Orders> order = orderRepository.findById(id);

        Customer customerResponse = getCustomerDetails(order.get().getCustomerId());
        if (customerResponse == null) {
            throw new IllegalArgumentException("Customer ID not found");
        }
        Customer customer = new Customer();
        customer.setId(customerResponse.getId());
        customer.setFirstname(customerResponse.getFirstname());
        customer.setLastname(customerResponse.getLastname());
        customer.setEmail(customerResponse.getEmail());
        customer.setAddress(customerResponse.getAddress());
        customer.setTelephone(customerResponse.getTelephone());
        customer.setBirthday(customerResponse.getBirthday());

        List<ProductOrdersResponse> productOrdersResponse = new ArrayList<>();

        for (ProductOrders productOrders : order.get().getProductOrders()) {
            Product productResponse = getProductDetails(productOrders.getProductId());
            if (productResponse == null) {
                throw new IllegalArgumentException("Product ID not found");
            }
            ProductOrdersResponse newProductOrdersResponse = new ProductOrdersResponse();
            newProductOrdersResponse.setId(productOrders.getId());
            newProductOrdersResponse.setProduct(productResponse);
            newProductOrdersResponse.setQuantity(productOrders.getQuantity());
            newProductOrdersResponse.setPrice(productOrders.getPrice());
            productOrdersResponse.add(newProductOrdersResponse);
        }
        return Optional.of(convertToOrderResponse(order.get(), customer, productOrdersResponse));
    }

    public void createOrders(Orders orders) {
        Customer customerResponse = getCustomerDetails(orders.getCustomerId());
        if (customerResponse == null) {
            throw new IllegalArgumentException("Customer ID not found");
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

        List<ProductOrders> productOrders = new ArrayList<>();

        for (ProductOrders productOrder : orders.getProductOrders()) {
            Product productResponse = getProductDetails(productOrder.getProductId());
            if (productResponse == null) {
                throw new IllegalArgumentException("Product ID not found");
            }
            ProductOrders newProductOrder = new ProductOrders();
            newProductOrder.setId(productOrder.getId());
            newProductOrder.setProductId(productOrder.getProductId());
            newProductOrder.setQuantity(productOrder.getQuantity());
            newProductOrder.setPrice(productOrder.getPrice());
            newProductOrder.setOrders(orders);
            productOrders.add(newProductOrder);
        }
        orders.setProductOrders(productOrders);
        orders.setShippingAddress(customerResponse.getAddress());
        orderRepository.save(orders);
    }

    public void updateOrders(Orders orders) {
        Orders existingOrders = orderRepository.findById(orders.getId())
                .orElseThrow(() -> new IllegalArgumentException("Order ID not found"));

        existingOrders.setCustomerId(orders.getCustomerId());
        existingOrders.setOrderDate(orders.getOrderDate());
        existingOrders.setStatus(orders.getStatus());
        existingOrders.setTotalPrice(orders.getTotalPrice());

        Customer customerResponse = getCustomerDetails(orders.getCustomerId());
        if (customerResponse == null) {
            throw new IllegalArgumentException("Customer ID not found");
        }
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(customerResponse.getId());
        updatedCustomer.setFirstname(customerResponse.getFirstname());
        updatedCustomer.setLastname(customerResponse.getLastname());
        updatedCustomer.setEmail(customerResponse.getEmail());
        updatedCustomer.setAddress(customerResponse.getAddress());
        updatedCustomer.setTelephone(customerResponse.getTelephone());
        updatedCustomer.setBirthday(customerResponse.getBirthday());

        existingOrders.setShippingAddress(customerResponse.getAddress());

        // Get Existing ProductOrders
        List<ProductOrders> existingProductOrders = existingOrders.getProductOrders();
        List<ProductOrders> updatedProductOrders = new ArrayList<>();
        List<ProductOrders> newProductOrders = new ArrayList<>();

        for (ProductOrders productOrder : orders.getProductOrders()) {
            Product productResponse = getProductDetails(productOrder.getProductId());
            if (productResponse == null) {
                throw new IllegalArgumentException("Product ID not found");
            }

            // Check if the product order already exists
            Optional<ProductOrders> existingProductOrdersOptional = existingProductOrders.stream()
                    .filter(p -> p.getProductId().equals(productOrder.getProductId())).findFirst();

            if (existingProductOrdersOptional.isPresent()) {
                ProductOrders existingProductOrder = existingProductOrdersOptional.get();
                existingProductOrder.setProductId(productOrder.getProductId());
                existingProductOrder.setQuantity(productOrder.getQuantity());
                existingProductOrder.setPrice(productOrder.getPrice());

                updatedProductOrders.add(existingProductOrder);

            } else {
                ProductOrders newProductOrder = new ProductOrders();
                newProductOrder.setId(productOrder.getId());
                newProductOrder.setProductId(productOrder.getProductId());
                newProductOrder.setQuantity(productOrder.getQuantity());
                newProductOrder.setPrice(productOrder.getPrice());
                newProductOrder.setOrders(existingOrders);

                newProductOrders.add(newProductOrder); // Add to new list
                updatedProductOrders.add(newProductOrder);
            }
        }

        // Remove orders not in the updated list
        existingProductOrders.removeIf(p -> updatedProductOrders.stream()
                .noneMatch(updated -> updated.getProductId().equals(p.getProductId())));

        // Add the new product orders to the existing collection
        existingProductOrders.addAll(newProductOrders);
        orderRepository.save(existingOrders);
    }

    public void updateOrdersV2(Orders orders) {
        orderRepository.delete(orders);
        orderRepository.save(orders);
    }

    public Orders saveOrder(Orders orders) {
        return orderRepository.save(orders);
    }

    public void deleteOrder(Long id) {
        Orders orders = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order ID not found"));

        orderRepository.delete(orders);
    }

    public List<OrdersResponse> getAllOrderStatus(String status) {
        List<Orders> orders = orderRepository.findAllByStatus(status);

        List<OrdersResponse> ordersResponse = new ArrayList<>();

        for (Orders order : orders) {
            Customer customerResponse = getCustomerDetails(order.getCustomerId());
            if (customerResponse == null) {
                throw new IllegalArgumentException("Customer ID not found");
            }
            Customer customer = new Customer();
            customer.setId(customerResponse.getId());
            customer.setFirstname(customerResponse.getFirstname());
            customer.setLastname(customerResponse.getLastname());
            customer.setEmail(customerResponse.getEmail());
            customer.setAddress(customerResponse.getAddress());
            customer.setTelephone(customerResponse.getTelephone());
            customer.setBirthday(customerResponse.getBirthday());

            List<ProductOrdersResponse> productOrdersResponse = new ArrayList<>();

            for (ProductOrders productOrders : order.getProductOrders()) {
                if (productOrders == null) {
                    throw new IllegalArgumentException("Product ID not found");
                }
                Product productResponse = getProductDetails(productOrders.getProductId());
                ProductOrdersResponse newProductOrdersResponse = new ProductOrdersResponse();
                newProductOrdersResponse.setId(productOrders.getId());
                newProductOrdersResponse.setProduct(productResponse);
                newProductOrdersResponse.setQuantity(productOrders.getQuantity());
                newProductOrdersResponse.setPrice(productOrders.getPrice());
                productOrdersResponse.add(newProductOrdersResponse);
            }
            ordersResponse.add(convertToOrderResponse(order, customer, productOrdersResponse));
        }
        return ordersResponse;
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

    // private ProductOrdersResponse convertToProductOrdersResponse(ProductOrders
    // productOrders) {
    // return new ProductOrdersResponse(
    // productOrders.getId(),
    // getProductDetails(productOrders.getProductId()),
    // productOrders.getQuantity(),
    // productOrders.getPrice());
    // }

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
