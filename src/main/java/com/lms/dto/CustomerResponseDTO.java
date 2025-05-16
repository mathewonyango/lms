package com.lms.dto;

public class CustomerResponseDTO {

    private String customerNumber;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String phoneNumber;
    private String email;

    // Constructor
    public CustomerResponseDTO(String customerNumber, String firstName, String lastName,
                               String idNumber, String phoneNumber, String email) {
        this.customerNumber = customerNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public CustomerResponseDTO() {
        this.customerNumber = "";
        this.firstName = "";
        this.lastName = "";
        this.idNumber = "";
        this.phoneNumber = "";
        this.email = "";
    }

    // Getters and Setters
    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
