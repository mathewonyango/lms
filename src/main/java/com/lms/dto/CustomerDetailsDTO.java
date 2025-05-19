package com.lms.dto;

import lombok.Data;

@Data
public class CustomerDetailsDTO {
    private Long customerId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String idNumber;
    private String phoneNumber;
    private String email;
    private String address;
    private String accountNumber;

    // Getters and setters
    // (You can generate with Lombok if desired)
}
