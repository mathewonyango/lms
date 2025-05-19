package com.lms.service;

import com.lms.client.SoapClient;
import com.lms.client.KycClient;
import com.lms.dto.CustomerDetailsDTO;
import com.lms.model.CustomerEntity;
import com.lms.repository.CustomerRepository;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;

@Service
public class KycService {

    private static final Logger logger = LoggerFactory.getLogger(KycService.class);

    @Autowired
    private KycClient KycClient;
    @Autowired
    private CustomerRepository customerRepository;


   public CustomerDetailsDTO getCustomerDetails(String customerNumber) throws JAXBException {
    try {
        logger.info("Fetching KYC data for customer: {}", customerNumber);

        String soapResponse = KycClient.getKycDetails(customerNumber);
        String customerXml = extractCustomerResponseFromSoap(soapResponse);

        JAXBContext jaxbContext = JAXBContext.newInstance(GetCustomerResponse.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        GetCustomerResponse response = (GetCustomerResponse) unmarshaller.unmarshal(new StringReader(customerXml));
        CustomerDetailsDTO dto = mapToDto(response.customer);

        // Persist to database
        CustomerEntity entity = mapToEntity(response.customer);
        customerRepository.save(entity);

        return dto;

    } catch (Exception e) {
        logger.error("Error retrieving KYC data", e);
        throw new JAXBException("Failed to parse KYC response", e);
    }
}


    private String extractCustomerResponseFromSoap(String soapResponse) {
        String startTag = "<getCustomerResponse>";
        String endTag = "</getCustomerResponse>";

        int startIndex = soapResponse.indexOf(startTag);
        int endIndex = soapResponse.indexOf(endTag) + endTag.length();

        if (startIndex == -1 || endIndex == -1) {
            throw new RuntimeException("Invalid SOAP response: Missing getCustomerResponse tags");
        }

        return soapResponse.substring(startIndex, endIndex);
    }

    private CustomerDetailsDTO mapToDto(Customer customer) {
        CustomerDetailsDTO dto = new CustomerDetailsDTO();
        dto.setCustomerId(customer.customerId);
        dto.setFirstName(customer.firstName);
        dto.setLastName(customer.lastName);
        dto.setDateOfBirth(customer.dateOfBirth);
        dto.setIdNumber(customer.idNumber);
        dto.setPhoneNumber(customer.phoneNumber);
        dto.setEmail(customer.email);
        dto.setAddress(customer.address);
        dto.setAccountNumber(customer.accountNumber);
        return dto;
    }


    private CustomerEntity mapToEntity(Customer customer) {
    CustomerEntity entity = new CustomerEntity();
    entity.setCustomerId(customer.customerId);
    entity.setFirstName(customer.firstName);
    entity.setLastName(customer.lastName);
    entity.setDateOfBirth(customer.dateOfBirth);
    entity.setIdNumber(customer.idNumber);
    entity.setPhoneNumber(customer.phoneNumber);
    entity.setEmail(customer.email);
    entity.setAddress(customer.address);
    entity.setAccountNumber(customer.accountNumber);
    return entity;
}


    // JAXB Model Classes
    @XmlRootElement(name = "getCustomerResponse")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class GetCustomerResponse {
        @XmlElement(name = "customer")
        private Customer customer;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    private static class Customer {
        @XmlElement(name = "customerId")
        private Long customerId;

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
    }
}
