package com.lms.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    private Long id;

    private String accountNumber;
    private Double credittransactionsAmount;
    private Double monthlydebittransactionsAmount;
    private Long atmTransactionsNumber;
    private Double atmtransactionsAmount;
    private Double monthlyBalance;

    private Double lastTransactionValue;
    private Long lastTransactionDate;
    private String lastTransactionType;
    private Double transactionValue;
    private Long createdAt;
    private Long updatedAt;
    private Long createdDate;
    private Double intrestAmount;
    private Double overdraftLimit;

    private Double debitcardpostransactionsAmount;
    private Long debitcardpostransactionsNumber;
    private Double mobilemoneycredittransactionAmount;
    private Long mobilemoneycredittransactionNumber;
    private Double mobilemoneydebittransactionAmount;
    private Long mobilemoneydebittransactionNumber;
    private Double overthecounterwithdrawalsAmount;
    private Long overthecounterwithdrawalsNumber;
    private Double chequeDebitTransactionsAmount;
    private Long chequeDebitTransactionsNumber;
}
