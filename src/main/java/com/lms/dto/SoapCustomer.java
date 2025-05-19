package com.lms.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
@Data

@XmlAccessorType(XmlAccessType.FIELD)
public class SoapCustomer {

    @XmlElement(name = "customerId")
    private String customerId;

    @XmlElement(name = "firstName")
    private String firstName;

    @XmlElement(name = "lastName")
    private String lastName;

    @XmlElement(name = "dateOfBirth")
    private String dateOfBirth;

    @XmlElement(name = "idNumber")
    private String idNumber;

    @XmlElement(name = "phoneNumber")
    private String phoneNumber;

    @XmlElement(name = "email")
    private String email;

    @XmlElement(name = "address")
    private String address;

    @XmlElement(name = "accountNumber")
    private String accountNumber;

    // Getters and Setters
}