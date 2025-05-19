package com.lms.controller;

import com.lms.service.KycService;

import jakarta.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lms.dto.CustomerDetailsDTO;


@RestController
@RequestMapping("/kyc")
public class KycController {

    @Autowired
    private KycService kycService;

    @GetMapping("/customer-details")
    public ResponseEntity<?> getCustomerDetails(@RequestParam("customerNumber") String customerNumber) {
        try {
            CustomerDetailsDTO customerDetails = kycService.getCustomerDetails(customerNumber);
            return ResponseEntity.ok(customerDetails);
        } catch (JAXBException e) {
            return ResponseEntity.status(500).body("Failed to retrieve customer details: " + e.getMessage());
        }
    }
}
