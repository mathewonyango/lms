package com.lms.dto;

public class TransactionStats {

    private double totalCreditAmount;
    private double totalDebitAmount;
    private double netAmount;
    private long totalTransactionCount;
    private int transactionRecordCount;

    private double averageMonthlyCredit;
    private double averageMonthlyBalance; // optional: for more exclusion rules

    // Getters and Setters

    public double getTotalCreditAmount() {
        return totalCreditAmount;
    }

    public void setTotalCreditAmount(double totalCreditAmount) {
        this.totalCreditAmount = totalCreditAmount;
    }

    public double getTotalDebitAmount() {
        return totalDebitAmount;
    }

    public void setTotalDebitAmount(double totalDebitAmount) {
        this.totalDebitAmount = totalDebitAmount;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public long getTotalTransactionCount() {
        return totalTransactionCount;
    }

    public void setTotalTransactionCount(long totalTransactionCount) {
        this.totalTransactionCount = totalTransactionCount;
    }

    public int getTransactionRecordCount() {
        return transactionRecordCount;
    }

    public void setTransactionRecordCount(int transactionRecordCount) {
        this.transactionRecordCount = transactionRecordCount;
    }

    public double getAverageMonthlyCredit() {
        return averageMonthlyCredit;
    }

    public void setAverageMonthlyCredit(double averageMonthlyCredit) {
        this.averageMonthlyCredit = averageMonthlyCredit;
    }

    public double getAverageMonthlyBalance() {
        return averageMonthlyBalance;
    }

    public void setAverageMonthlyBalance(double averageMonthlyBalance) {
        this.averageMonthlyBalance = averageMonthlyBalance;
    }
}
