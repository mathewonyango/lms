package com.lms.dto;

public class CustomerRequestDTO {

    private String customerNumber;

    // Constructor
    public CustomerRequestDTO(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    // Getters and Setters
    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }
}
