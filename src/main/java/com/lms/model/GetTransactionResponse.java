package com.lms.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getTransactionResponse")
public class GetTransactionResponse {

    @XmlElement(name = "transactions")
    private TransactionsWrapper transactions;

    public TransactionsWrapper getTransactions() {
        return transactions;
    }

    public void setTransactions(TransactionsWrapper transactions) {
        this.transactions = transactions;
    }
}