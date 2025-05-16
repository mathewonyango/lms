package com.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDataDTO {
    private String accountNumber;
    private Double alternativechanneltrnscrAmount;
    private Integer alternativechanneltrnscrNumber;
    private Double alternativechanneltrnsdebitAmount;
    private Long alternativechanneltrnsdebitNumber;
    private Integer atmTransactionsNumber;
    private Double atmtransactionsAmount;
    private Integer bouncedChequesDebitNumber;
    private Integer bouncedchequescreditNumber;
    private Double bouncedchequetransactionscrAmount;
    private Double bouncedchequetransactionsdrAmount;
    private Double chequeDebitTransactionsAmount;
    private Integer chequeDebitTransactionsNumber;
    private Long createdAt;
    private Long createdDate;
    private Double credittransactionsAmount;
    private Double debitcardpostransactionsAmount;
    private Integer debitcardpostransactionsNumber;
    private Double fincominglocaltransactioncrAmount;
    private Long id;
    private Double incominginternationaltrncrAmount;
    private Integer incominginternationaltrncrNumber;
    private Integer incominglocaltransactioncrNumber;
    private Integer intrestAmount;
    private Long lastTransactionDate;
    private String lastTransactionType;
    private Integer lastTransactionValue;
    private Double maxAtmTransactions;
}
