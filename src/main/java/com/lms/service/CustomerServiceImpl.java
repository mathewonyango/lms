package com.lms.service;

import com.credable.credable.soap.generated.CustomerRequest;
import com.credable.credable.soap.generated.CustomerResponse;
import com.lms.dto.CustomerResponseDTO;
import com.lms.model.Customer;
import com.lms.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Optional;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final WebServiceTemplate kycWebServiceTemplate;

    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            @Qualifier("customerWebServiceTemplate") WebServiceTemplate kycWebServiceTemplate) {
        this.customerRepository = customerRepository;
        this.kycWebServiceTemplate = kycWebServiceTemplate;
    }

    @Override
    public CustomerResponseDTO getCustomerDetails(String customerNumber) {
        log.info("Fetching customer details for customer number: {}", customerNumber);

        CustomerRequest request = new CustomerRequest();
        request.setCustomerNumber(customerNumber);

        Object response = kycWebServiceTemplate.marshalSendAndReceive(request);

        if (response instanceof CustomerResponseDTO customerResponse) {
            log.info("Successfully retrieved customer details");
            
            // Map CustomerResponse to CustomerResponseDTO manually if needed
            CustomerResponseDTO dto = new CustomerResponseDTO();
            dto.setCustomerNumber(customerResponse.getCustomerNumber());
            dto.setFirstName(customerResponse.getFirstName());
            dto.setLastName(customerResponse.getLastName());
            dto.setIdNumber(customerResponse.getIdNumber());
            dto.setPhoneNumber(customerResponse.getPhoneNumber());
            dto.setEmail(customerResponse.getEmail());
            
            return dto;
        }

        log.error("Failed to retrieve customer details for customer number: {}", customerNumber);
        throw new RuntimeException("Failed to retrieve customer details");
    }

    @Override
    public Optional<Customer> findByCustomerNumber(String customerNumber) {
        return customerRepository.findByCustomerNumber(customerNumber);
    }

    @Override
    public boolean existsByCustomerNumber(String customerNumber) {
        return customerRepository.existsByCustomerNumber(customerNumber);
    }

    @Override
    public Optional<String> getCustomerNumber(String customerNumber) {
        if (existsByCustomerNumber(customerNumber)) {
            return Optional.of(customerNumber);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Customer findOrCreateCustomer(CustomerResponseDTO customerResponse) {
        return customerRepository.findByCustomerNumber(customerResponse.getCustomerNumber())
                .orElseGet(() -> {
                    Customer customer = new Customer();
                    customer.setCustomerNumber(customerResponse.getCustomerNumber());
                    customer.setFirstName(customerResponse.getFirstName());
                    customer.setLastName(customerResponse.getLastName());
                    customer.setIdNumber(customerResponse.getIdNumber());
                    customer.setMobile(customerResponse.getPhoneNumber());
                    customer.setEmail(customerResponse.getEmail());
                    return customerRepository.save(customer);
                });
    }
}
