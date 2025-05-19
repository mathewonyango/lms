package com.lms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class CustomerEntity {

    @Id
    private Long customerId;

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String idNumber;
    private String phoneNumber;
    private String email;
    private String address;
    private String accountNumber;
}