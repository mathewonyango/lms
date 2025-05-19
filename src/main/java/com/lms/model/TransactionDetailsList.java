package com.lms.model;

import java.util.List;

public class TransactionDetailsList {
    
    private List<TransactionDetailsDTO> transactions;
    private int totalCount;
    private String status;
    private String message;
    
    // Default constructor
    public TransactionDetailsList() {}
    
    // Constructor with transactions list
    public TransactionDetailsList(List<TransactionDetailsDTO> transactions) {
        this.transactions = transactions;
        this.totalCount = transactions != null ? transactions.size() : 0;
        this.status = "SUCCESS";
        this.message = "Transactions retrieved successfully";
    }
    
    // Constructor with all fields
    public TransactionDetailsList(List<TransactionDetailsDTO> transactions, String status, String message) {
        this.transactions = transactions;
        this.totalCount = transactions != null ? transactions.size() : 0;
        this.status = status;
        this.message = message;
    }
    
    // Getters and Setters
    public List<TransactionDetailsDTO> getTransactions() {
        return transactions;
    }
    
    public void setTransactions(List<TransactionDetailsDTO> transactions) {
        this.transactions = transactions;
        this.totalCount = transactions != null ? transactions.size() : 0;
    }
    
    public int getTotalCount() {
        return totalCount;
    }
    
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    // Utility methods
    public boolean isEmpty() {
        return transactions == null || transactions.isEmpty();
    }
    
    public boolean hasTransactions() {
        return !isEmpty();
    }
    
    @Override
    public String toString() {
        return "TransactionDetailsList{" +
                "totalCount=" + totalCount +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", hasTransactions=" + hasTransactions() +
                '}';
    }
}
