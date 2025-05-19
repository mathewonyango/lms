package com.lms.dto;

import lombok.Data;

@Data
public class ScoreDTO {
    private String customerNumber;

    private double averageMonthlyCredit;
    private double averageMonthlyDebit;
    private double averageMonthlyBalance;

    private double limitAmount;
    private double weeklyLimit;
    private double monthlyLimit;

    private String expenditureBehavior;
    private boolean isExcluded;
    private String exclusionReason;
    public Object getAccountNumber() {
        // TODO Auto-generated method stub
        return this.customerNumber;
    }

   
    

}

