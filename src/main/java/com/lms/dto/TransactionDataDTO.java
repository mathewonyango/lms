package com.lms.dto;

import jakarta.xml.bind.annotation.*;
import java.util.List;

// Root element annotation for JAXB
@XmlRootElement(name = "transactionData") // Adjust this to match your actual XML root element
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionDataDTO {
    
    @XmlElement(name = "accountNumber")
    private String accountNumber;
    
    @XmlElement(name = "transactions")
    private List<Transaction> transactions;
    
    @XmlElement(name = "totalAmount")
    private Double totalAmount;
    
    @XmlElement(name = "currency")
    private String currency;
    
    // Default constructor (required for JAXB)
    public TransactionDataDTO() {}
    
    // Constructor with parameters
    public TransactionDataDTO(String accountNumber, List<Transaction> transactions, 
                            Double totalAmount, String currency) {
        this.accountNumber = accountNumber;
        this.transactions = transactions;
        this.totalAmount = totalAmount;
        this.currency = currency;
    }
    
    // Getters and setters
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    public Double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    @Override
    public String toString() {
        return "TransactionDataDTO{" +
                "accountNumber='" + accountNumber + '\'' +
                ", transactions=" + transactions +
                ", totalAmount=" + totalAmount +
                ", currency='" + currency + '\'' +
                '}';
    }
    
    // Inner class for Transaction
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Transaction {
        
        @XmlElement(name = "transactionId")
        private String transactionId;
        
        @XmlElement(name = "amount")
        private Double amount;
        
        @XmlElement(name = "type")
        private String type;
        
        @XmlElement(name = "description")
        private String description;
        
        @XmlElement(name = "date")
        private String date;
        
        @XmlElement(name = "balance")
        private Double balance;
        
        // Default constructor
        public Transaction() {}
        
        // Constructor with parameters
        public Transaction(String transactionId, Double amount, String type, 
                         String description, String date, Double balance) {
            this.transactionId = transactionId;
            this.amount = amount;
            this.type = type;
            this.description = description;
            this.date = date;
            this.balance = balance;
        }
        
        // Getters and setters
        public String getTransactionId() {
            return transactionId;
        }
        
        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }
        
        public Double getAmount() {
            return amount;
        }
        
        public void setAmount(Double amount) {
            this.amount = amount;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public String getDescription() {
            return description;
        }
        
        public void setDescription(String description) {
            this.description = description;
        }
        
        public String getDate() {
            return date;
        }
        
        public void setDate(String date) {
            this.date = date;
        }
        
        public Double getBalance() {
            return balance;
        }
        
        public void setBalance(Double balance) {
            this.balance = balance;
        }
        
        @Override
        public String toString() {
            return "Transaction{" +
                    "transactionId='" + transactionId + '\'' +
                    ", amount=" + amount +
                    ", type='" + type + '\'' +
                    ", description='" + description + '\'' +
                    ", date='" + date + '\'' +
                    ", balance=" + balance +
                    '}';
        }
    }
}