package com.lms.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "transactionDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionDetailsDTO {
    
    // Financial transaction amounts
    private Double credittransactionsAmount;
    private Double lastTransactionValue;
    private Double outgoinginttransactiondebitAmount;
    private Double debitcardpostransactionsAmount;
    private Double minmonthlycredittransactions;
    private Double maxMonthlyBebitTransactions;
    private Double maxAtmTransactions;
    private Double alternativechanneltrnsdebitAmount;
    private Double monthlyBalance;
    private Double fincominglocaltransactioncrAmount;
    private Double outgoinglocaltransactiondebitAmount;
    private Double minoverthecounterwithdrawals;
    private Double alternativechanneltrnscrAmount;
    private Double mobilemoneycredittransactionAmount;
    private Double incominginternationaltrncrAmount;
    private Double maxdebitcardpostransactions;
    private Double overdraftLimit;
    private Double minmobilemoneydebittransaction;
    private Double bouncedchequetransactionscrAmount;
    private Double bouncedchequetransactionsdrAmount;
    private Double monthlydebittransactionsAmount;
    private Double mobilemoneydebittransactionAmount;
    private Double atmtransactionsAmount;
    private Double chequeDebitTransactionsAmount;
    private Double intrestAmount;
    private Double transactionValue;
    private Double overthecounterwithdrawalsAmount;
    
    // Transaction counts/numbers
    private Long overthecounterwithdrawalsNumber;
    private Long outgoinginttrndebitNumber;
    private Long alternativechanneltrnsdebitNumber;
    private Long incominginternationaltrncrNumber;
    private Long outgoinglocaltransactiondebitNumber;
    private Long alternativechanneltrnscrNumber;
    private Long mobilemoneycredittransactionNumber;
    private Long atmTransactionsNumber;
    private Long incominglocaltransactioncrNumber;
    private Long debitcardpostransactionsNumber;
    private Long id;
    private Long bouncedChequesDebitNumber;
    private Long mobilemoneydebittransactionNumber;
    private Long bouncedchequescreditNumber;
    private Long chequeDebitTransactionsNumber;
    
    // Dates and timestamps
    private Long createdAt;
    private Long lastTransactionDate;
    private Long updatedAt;
    private Long createdDate;
    
    // String fields
    private String lastTransactionType;
    private String accountNumber;
    
    // Default constructor
    public TransactionDetailsDTO() {}
    
    // Getters and Setters
    public Double getCredittransactionsAmount() {
        return credittransactionsAmount;
    }
    
    public void setCredittransactionsAmount(Double credittransactionsAmount) {
        this.credittransactionsAmount = credittransactionsAmount;
    }
    
    public Long getOverthecounterwithdrawalsNumber() {
        return overthecounterwithdrawalsNumber;
    }
    
    public void setOverthecounterwithdrawalsNumber(Long overthecounterwithdrawalsNumber) {
        this.overthecounterwithdrawalsNumber = overthecounterwithdrawalsNumber;
    }
    
    public Double getLastTransactionValue() {
        return lastTransactionValue;
    }
    
    public void setLastTransactionValue(Double lastTransactionValue) {
        this.lastTransactionValue = lastTransactionValue;
    }
    
    public Double getOutgoinginttransactiondebitAmount() {
        return outgoinginttransactiondebitAmount;
    }
    
    public void setOutgoinginttransactiondebitAmount(Double outgoinginttransactiondebitAmount) {
        this.outgoinginttransactiondebitAmount = outgoinginttransactiondebitAmount;
    }
    
    public Long getOutgoinginttrndebitNumber() {
        return outgoinginttrndebitNumber;
    }
    
    public void setOutgoinginttrndebitNumber(Long outgoinginttrndebitNumber) {
        this.outgoinginttrndebitNumber = outgoinginttrndebitNumber;
    }
    
    public Long getAlternativechanneltrnsdebitNumber() {
        return alternativechanneltrnsdebitNumber;
    }
    
    public void setAlternativechanneltrnsdebitNumber(Long alternativechanneltrnsdebitNumber) {
        this.alternativechanneltrnsdebitNumber = alternativechanneltrnsdebitNumber;
    }
    
    public Long getIncominginternationaltrncrNumber() {
        return incominginternationaltrncrNumber;
    }
    
    public void setIncominginternationaltrncrNumber(Long incominginternationaltrncrNumber) {
        this.incominginternationaltrncrNumber = incominginternationaltrncrNumber;
    }
    
    public Double getDebitcardpostransactionsAmount() {
        return debitcardpostransactionsAmount;
    }
    
    public void setDebitcardpostransactionsAmount(Double debitcardpostransactionsAmount) {
        this.debitcardpostransactionsAmount = debitcardpostransactionsAmount;
    }
    
    public Long getOutgoinglocaltransactiondebitNumber() {
        return outgoinglocaltransactiondebitNumber;
    }
    
    public void setOutgoinglocaltransactiondebitNumber(Long outgoinglocaltransactiondebitNumber) {
        this.outgoinglocaltransactiondebitNumber = outgoinglocaltransactiondebitNumber;
    }
    
    public Long getAlternativechanneltrnscrNumber() {
        return alternativechanneltrnscrNumber;
    }
    
    public void setAlternativechanneltrnscrNumber(Long alternativechanneltrnscrNumber) {
        this.alternativechanneltrnscrNumber = alternativechanneltrnscrNumber;
    }
    
    public Long getMobilemoneycredittransactionNumber() {
        return mobilemoneycredittransactionNumber;
    }
    
    public void setMobilemoneycredittransactionNumber(Long mobilemoneycredittransactionNumber) {
        this.mobilemoneycredittransactionNumber = mobilemoneycredittransactionNumber;
    }
    
    public Long getAtmTransactionsNumber() {
        return atmTransactionsNumber;
    }
    
    public void setAtmTransactionsNumber(Long atmTransactionsNumber) {
        this.atmTransactionsNumber = atmTransactionsNumber;
    }
    
    public Long getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
    
    public Double getMinmonthlycredittransactions() {
        return minmonthlycredittransactions;
    }
    
    public void setMinmonthlycredittransactions(Double minmonthlycredittransactions) {
        this.minmonthlycredittransactions = minmonthlycredittransactions;
    }
    
    public Double getMaxMonthlyBebitTransactions() {
        return maxMonthlyBebitTransactions;
    }
    
    public void setMaxMonthlyBebitTransactions(Double maxMonthlyBebitTransactions) {
        this.maxMonthlyBebitTransactions = maxMonthlyBebitTransactions;
    }
    
    public Long getLastTransactionDate() {
        return lastTransactionDate;
    }
    
    public void setLastTransactionDate(Long lastTransactionDate) {
        this.lastTransactionDate = lastTransactionDate;
    }
    
    public Double getMaxAtmTransactions() {
        return maxAtmTransactions;
    }
    
    public void setMaxAtmTransactions(Double maxAtmTransactions) {
        this.maxAtmTransactions = maxAtmTransactions;
    }
    
    public Long getIncominglocaltransactioncrNumber() {
        return incominglocaltransactioncrNumber;
    }
    
    public void setIncominglocaltransactioncrNumber(Long incominglocaltransactioncrNumber) {
        this.incominglocaltransactioncrNumber = incominglocaltransactioncrNumber;
    }
    
    public Double getAlternativechanneltrnsdebitAmount() {
        return alternativechanneltrnsdebitAmount;
    }
    
    public void setAlternativechanneltrnsdebitAmount(Double alternativechanneltrnsdebitAmount) {
        this.alternativechanneltrnsdebitAmount = alternativechanneltrnsdebitAmount;
    }
    
    public Double getMonthlyBalance() {
        return monthlyBalance;
    }
    
    public void setMonthlyBalance(Double monthlyBalance) {
        this.monthlyBalance = monthlyBalance;
    }
    
    public Long getDebitcardpostransactionsNumber() {
        return debitcardpostransactionsNumber;
    }
    
    public void setDebitcardpostransactionsNumber(Long debitcardpostransactionsNumber) {
        this.debitcardpostransactionsNumber = debitcardpostransactionsNumber;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getBouncedChequesDebitNumber() {
        return bouncedChequesDebitNumber;
    }
    
    public void setBouncedChequesDebitNumber(Long bouncedChequesDebitNumber) {
        this.bouncedChequesDebitNumber = bouncedChequesDebitNumber;
    }
    
    public Double getFincominglocaltransactioncrAmount() {
        return fincominglocaltransactioncrAmount;
    }
    
    public void setFincominglocaltransactioncrAmount(Double fincominglocaltransactioncrAmount) {
        this.fincominglocaltransactioncrAmount = fincominglocaltransactioncrAmount;
    }
    
    public Double getOutgoinglocaltransactiondebitAmount() {
        return outgoinglocaltransactiondebitAmount;
    }
    
    public void setOutgoinglocaltransactiondebitAmount(Double outgoinglocaltransactiondebitAmount) {
        this.outgoinglocaltransactiondebitAmount = outgoinglocaltransactiondebitAmount;
    }
    
    public Double getMinoverthecounterwithdrawals() {
        return minoverthecounterwithdrawals;
    }
    
    public void setMinoverthecounterwithdrawals(Double minoverthecounterwithdrawals) {
        this.minoverthecounterwithdrawals = minoverthecounterwithdrawals;
    }
    
    public Double getAlternativechanneltrnscrAmount() {
        return alternativechanneltrnscrAmount;
    }
    
    public void setAlternativechanneltrnscrAmount(Double alternativechanneltrnscrAmount) {
        this.alternativechanneltrnscrAmount = alternativechanneltrnscrAmount;
    }
    
    public Long getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Double getMobilemoneycredittransactionAmount() {
        return mobilemoneycredittransactionAmount;
    }
    
    public void setMobilemoneycredittransactionAmount(Double mobilemoneycredittransactionAmount) {
        this.mobilemoneycredittransactionAmount = mobilemoneycredittransactionAmount;
    }
    
    public Long getMobilemoneydebittransactionNumber() {
        return mobilemoneydebittransactionNumber;
    }
    
    public void setMobilemoneydebittransactionNumber(Long mobilemoneydebittransactionNumber) {
        this.mobilemoneydebittransactionNumber = mobilemoneydebittransactionNumber;
    }
    
    public Double getIncominginternationaltrncrAmount() {
        return incominginternationaltrncrAmount;
    }
    
    public void setIncominginternationaltrncrAmount(Double incominginternationaltrncrAmount) {
        this.incominginternationaltrncrAmount = incominginternationaltrncrAmount;
    }
    
    public String getLastTransactionType() {
        return lastTransactionType;
    }
    
    public void setLastTransactionType(String lastTransactionType) {
        this.lastTransactionType = lastTransactionType;
    }
    
    public Double getMaxdebitcardpostransactions() {
        return maxdebitcardpostransactions;
    }
    
    public void setMaxdebitcardpostransactions(Double maxdebitcardpostransactions) {
        this.maxdebitcardpostransactions = maxdebitcardpostransactions;
    }
    
    public Double getOverdraftLimit() {
        return overdraftLimit;
    }
    
    public void setOverdraftLimit(Double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
    
    public Double getMinmobilemoneydebittransaction() {
        return minmobilemoneydebittransaction;
    }
    
    public void setMinmobilemoneydebittransaction(Double minmobilemoneydebittransaction) {
        this.minmobilemoneydebittransaction = minmobilemoneydebittransaction;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public Double getBouncedchequetransactionscrAmount() {
        return bouncedchequetransactionscrAmount;
    }
    
    public void setBouncedchequetransactionscrAmount(Double bouncedchequetransactionscrAmount) {
        this.bouncedchequetransactionscrAmount = bouncedchequetransactionscrAmount;
    }
    
    public Double getBouncedchequetransactionsdrAmount() {
        return bouncedchequetransactionsdrAmount;
    }
    
    public void setBouncedchequetransactionsdrAmount(Double bouncedchequetransactionsdrAmount) {
        this.bouncedchequetransactionsdrAmount = bouncedchequetransactionsdrAmount;
    }
    
    public Double getMonthlydebittransactionsAmount() {
        return monthlydebittransactionsAmount;
    }
    
    public void setMonthlydebittransactionsAmount(Double monthlydebittransactionsAmount) {
        this.monthlydebittransactionsAmount = monthlydebittransactionsAmount;
    }
    
    public Double getMobilemoneydebittransactionAmount() {
        return mobilemoneydebittransactionAmount;
    }
    
    public void setMobilemoneydebittransactionAmount(Double mobilemoneydebittransactionAmount) {
        this.mobilemoneydebittransactionAmount = mobilemoneydebittransactionAmount;
    }
    
    public Double getAtmtransactionsAmount() {
        return atmtransactionsAmount;
    }
    
    public void setAtmtransactionsAmount(Double atmtransactionsAmount) {
        this.atmtransactionsAmount = atmtransactionsAmount;
    }
    
    public Long getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }
    
    public Long getBouncedchequescreditNumber() {
        return bouncedchequescreditNumber;
    }
    
    public void setBouncedchequescreditNumber(Long bouncedchequescreditNumber) {
        this.bouncedchequescreditNumber = bouncedchequescreditNumber;
    }
    
    public Long getChequeDebitTransactionsNumber() {
        return chequeDebitTransactionsNumber;
    }
    
    public void setChequeDebitTransactionsNumber(Long chequeDebitTransactionsNumber) {
        this.chequeDebitTransactionsNumber = chequeDebitTransactionsNumber;
    }
    
    public Double getChequeDebitTransactionsAmount() {
        return chequeDebitTransactionsAmount;
    }
    
    public void setChequeDebitTransactionsAmount(Double chequeDebitTransactionsAmount) {
        this.chequeDebitTransactionsAmount = chequeDebitTransactionsAmount;
    }
    
    public Double getIntrestAmount() {
        return intrestAmount;
    }
    
    public void setIntrestAmount(Double intrestAmount) {
        this.intrestAmount = intrestAmount;
    }
    
    public Double getTransactionValue() {
        return transactionValue;
    }
    
    public void setTransactionValue(Double transactionValue) {
        this.transactionValue = transactionValue;
    }
    
    public Double getOverthecounterwithdrawalsAmount() {
        return overthecounterwithdrawalsAmount;
    }
    
    public void setOverthecounterwithdrawalsAmount(Double overthecounterwithdrawalsAmount) {
        this.overthecounterwithdrawalsAmount = overthecounterwithdrawalsAmount;
    }
}