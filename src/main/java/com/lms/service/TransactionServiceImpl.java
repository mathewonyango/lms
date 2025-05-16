package com.lms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.credable.credable.soap.generated.ObjectFactory;
import com.credable.credable.soap.generated.TransactionsRequest;
import com.credable.credable.soap.generated.TransactionsResponse;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final WebServiceTemplate transactionWebServiceTemplate;
    private final ObjectFactory objectFactory = new ObjectFactory();

    @Override
    public TransactionsResponse getCustomerTransactions(String customerNumber) {
        log.info("Fetching transaction data for customer: {}", customerNumber);
        
        TransactionsRequest request = objectFactory.createTransactionsRequest();
        request.setCustomerNumber(customerNumber);
        
        Object response = transactionWebServiceTemplate.marshalSendAndReceive(request);
        
        if (response instanceof TransactionsResponse) {
            log.info("Successfully retrieved transaction data");
            return (TransactionsResponse) response;
        }
        
        log.error("Failed to retrieve transaction data for customer: {}", customerNumber);
        throw new RuntimeException("Failed to retrieve transaction data");
    }

    @Override
    public List<Object> getTransactionData(String customerNumber) {
        TransactionsResponse response = getCustomerTransactions(customerNumber);
        
        List<Object> transactionDataList = new ArrayList<>();
        if (response != null && response.getTransactions() != null) {
            transactionDataList.addAll(response.getTransactions());
        }
        
        return transactionDataList;
    }
}