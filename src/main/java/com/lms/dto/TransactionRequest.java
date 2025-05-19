package com.lms.dto;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotBlank;

public class TransactionRequest {
    
    
    private String customerNumber;
    
    // Optional: Add more filters if needed
    private String accountNumber;
    private String dateFrom;
    private String dateTo;
    private String transactionType;
    private Integer limit;
    private Integer offset;
    
    // Default constructor
    public TransactionRequest() {}
    
    // Constructor with customer number
    public TransactionRequest(String customerNumber) {
        this.customerNumber = customerNumber;
    }
    
    // Getters and Setters
    public String getCustomerNumber() {
        return customerNumber;
    }
    
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getDateFrom() {
        return dateFrom;
    }
    
    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }
    
    public String getDateTo() {
        return dateTo;
    }
    
    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    
    public Integer getLimit() {
        return limit;
    }
    
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
    
    public Integer getOffset() {
        return offset;
    }
    
    public void setOffset(Integer offset) {
        this.offset = offset;
    }
    
    @Override
    public String toString() {
        return "TransactionRequest{" +
                "customerNumber='" + customerNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", limit=" + limit +
                ", offset=" + offset +
                '}';
    }
}