package com.lms.service;

import com.lms.dto.CustomerResponseDTO;
import com.lms.model.Customer;

import java.util.Optional;

public interface CustomerService {
    CustomerResponseDTO getCustomerDetails(String customerNumber);
    Customer findOrCreateCustomer(CustomerResponseDTO customerResponse);
    Optional<Customer> findByCustomerNumber(String customerNumber);
    boolean existsByCustomerNumber(String customerNumber);
    //getCustomerNumber
    Optional<String> getCustomerNumber(String customerNumber);
}

