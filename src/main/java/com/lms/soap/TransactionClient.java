package com.lms.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.lms.dto.TransactionRequest;
import com.lms.model.TransactionDetails;
import com.lms.model.TransactionDetailsList;

import jakarta.xml.bind.JAXBException;

@Component
public class TransactionClient extends WebServiceGatewaySupport {

    // URL of the SOAP endpoint
    private static final String SOAP_URL = "http://127.0.0.1:8090/api/v1/cbs/transactions";

    @Autowired
    public TransactionClient() {
        // Optional: Configure the client if necessary (e.g., add interceptors, etc.)
    }

    // Method to get a single transaction detail
    public TransactionDetails fetchTransactionDetails(String customerNumber) throws JAXBException {
        // Create the SOAP request object
        TransactionRequest request = new TransactionRequest();
        request.setCustomerNumber(customerNumber);

        // Send the SOAP request and receive the response
        TransactionDetails response = (TransactionDetails) getWebServiceTemplate()
                .marshalSendAndReceive(SOAP_URL, request);

        return response;
    }

    // Method to get a list of transaction details
    public TransactionDetailsList fetchTransactionDetailsList(String customerNumber) throws JAXBException {
        // Create the SOAP request object
        TransactionRequest request = new TransactionRequest();
        request.setCustomerNumber(customerNumber);

        // Send the SOAP request and receive the response
        TransactionDetailsList response = (TransactionDetailsList) getWebServiceTemplate()
                .marshalSendAndReceive(SOAP_URL, request);

        return response;
    }
}