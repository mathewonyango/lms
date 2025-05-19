package com.lms.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "getCustomerResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetCustomerResponse {

    @XmlElement(name = "customer")
    private SoapCustomer customer;

    public SoapCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(SoapCustomer customer) {
        this.customer = customer;
    }
}