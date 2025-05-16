package com.lms.service;
import com.credable.credable.soap.generated.TransactionsResponse;

import java.util.List;

public interface TransactionService {
    TransactionsResponse getCustomerTransactions(String customerNumber);
    List<Object> getTransactionData(String customerNumber);
}
