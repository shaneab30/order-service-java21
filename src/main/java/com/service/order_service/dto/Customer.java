package com.service.order_service.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private String telephone;
    private LocalDate birthday;
}
