package com.lms.service;

import com.lms.client.SoapClient;
import com.lms.model.TransactionDetailsDTO;
import com.lms.model.TransactionDetailsList;
import com.lms.model.TransactionEntity;
import com.lms.repository.TransactionRepository;

import jakarta.xml.bind.JAXBException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {
    
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    
    @Autowired
    private SoapClient soapClient;
    @Autowired
    private TransactionRepository transactionRepository;

    
    /**
     * Get transaction data for a specific customer
     * @param customerNumber The customer number to fetch transactions for
     * @return TransactionDetailsList containing all transaction data
     * @throws JAXBException if there's an error parsing the SOAP response
     */
    public TransactionDetailsList getTransactionData(String customerNumber) throws JAXBException {
        try {
            logger.info("Fetching transaction data for customer: {}", customerNumber);
            
            // Call SOAP service to get transaction data
            String soapResponse = soapClient.getTransactions(customerNumber);
            
            logger.debug("Received SOAP response for customer: {}", customerNumber);
            
            // Parse SOAP response and extract transaction data
            TransactionDetailsList transactionData = parseSoapResponse(soapResponse);
            
            logger.info("Successfully parsed {} transactions for customer: {}", 
                       transactionData.getTotalCount(), customerNumber);
            
            return transactionData;
            
        } catch (Exception e) {
            logger.error("Error fetching transaction data for customer: {}", customerNumber, e);
            if (e instanceof JAXBException) {
                throw e;
            }
            throw new RuntimeException("Failed to retrieve transaction data", e);
        }
    }
    
    /**
     * Parse SOAP response XML and convert to TransactionDetailsList
     * @param soapResponse The raw SOAP response XML
     * @return TransactionDetailsList object
     * @throws JAXBException if parsing fails
     */
    private TransactionDetailsList parseSoapResponse(String soapResponse) throws JAXBException {
        try {
            // Extract the transaction response from SOAP envelope
            String transactionXml = extractTransactionResponseFromSoap(soapResponse);
            
            // Create JAXB context and unmarshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(GetTransactionResponse.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            
            // Parse the XML
            StringReader reader = new StringReader(transactionXml);
            GetTransactionResponse response = (GetTransactionResponse) unmarshaller.unmarshal(reader);
            
            // Convert to our internal model
            return convertToTransactionDetailsList(response);
            
        } catch (Exception e) {
            logger.error("Error parsing SOAP response", e);
            throw new JAXBException("Failed to parse transaction data from SOAP response", e);
        }
    }
    
    /**
     * Extract transaction response XML from SOAP envelope
     * @param soapResponse The complete SOAP response
     * @return The transaction response XML portion
     */
    private String extractTransactionResponseFromSoap(String soapResponse) {
        try {
            // Find the start and end of the getTransactionResponse in the SOAP response
            String startTag = "<getTransactionResponse>";
            String endTag = "</getTransactionResponse>";
            
            int startIndex = soapResponse.indexOf(startTag);
            if (startIndex == -1) {
                throw new RuntimeException("Could not find getTransactionResponse in SOAP response");
            }
            
            int endIndex = soapResponse.indexOf(endTag) + endTag.length();
            if (endIndex == -1) {
                throw new RuntimeException("Could not find end of getTransactionResponse in SOAP response");
            }
            
            // Extract the transaction response XML
            return soapResponse.substring(startIndex, endIndex);
            
        } catch (Exception e) {
            logger.error("Error extracting transaction response from SOAP response", e);
            throw new RuntimeException("Failed to extract transaction response from SOAP response", e);
        }
    }
    
    /**
     * Convert SOAP response model to our internal TransactionDetailsList
     * @param response The parsed SOAP response
     * @return TransactionDetailsList object
     */
   private TransactionDetailsList convertToTransactionDetailsList(GetTransactionResponse response) {
    TransactionDetailsList result = new TransactionDetailsList();
    List<TransactionDetailsDTO> transactions = new ArrayList<>();

    if (response.transactions != null && response.transactions.transactionDetails != null) {
        for (SoapTransactionDetails soapTransaction : response.transactions.transactionDetails) {
            TransactionDetailsDTO dto = new TransactionDetailsDTO();

            // Map fields from SOAP response to DTO
            dto.setId(soapTransaction.id);
            dto.setAccountNumber(soapTransaction.accountNumber);
            dto.setCredittransactionsAmount(soapTransaction.credittransactionsAmount);
            dto.setMonthlydebittransactionsAmount(soapTransaction.monthlydebittransactionsAmount);
            dto.setAtmTransactionsNumber(soapTransaction.atmTransactionsNumber);
            dto.setAtmtransactionsAmount(soapTransaction.atmtransactionsAmount);
            dto.setMonthlyBalance(soapTransaction.monthlyBalance);

            dto.setLastTransactionValue(soapTransaction.lastTransactionValue);
            dto.setLastTransactionDate(soapTransaction.lastTransactionDate);
            dto.setLastTransactionType(soapTransaction.lastTransactionType);
            dto.setTransactionValue(soapTransaction.transactionValue);
            dto.setCreatedAt(soapTransaction.createdAt);
            dto.setUpdatedAt(soapTransaction.updatedAt);
            dto.setCreatedDate(soapTransaction.createdDate);
            dto.setIntrestAmount(soapTransaction.intrestAmount);
            dto.setOverdraftLimit(soapTransaction.overdraftLimit);

            dto.setDebitcardpostransactionsAmount(soapTransaction.debitcardpostransactionsAmount);
            dto.setDebitcardpostransactionsNumber(soapTransaction.debitcardpostransactionsNumber);
            dto.setMobilemoneycredittransactionAmount(soapTransaction.mobilemoneycredittransactionAmount);
            dto.setMobilemoneycredittransactionNumber(soapTransaction.mobilemoneycredittransactionNumber);
            dto.setMobilemoneydebittransactionAmount(soapTransaction.mobilemoneydebittransactionAmount);
            dto.setMobilemoneydebittransactionNumber(soapTransaction.mobilemoneydebittransactionNumber);
            dto.setOverthecounterwithdrawalsAmount(soapTransaction.overthecounterwithdrawalsAmount);
            dto.setOverthecounterwithdrawalsNumber(soapTransaction.overthecounterwithdrawalsNumber);
            dto.setChequeDebitTransactionsAmount(soapTransaction.chequeDebitTransactionsAmount);
            dto.setChequeDebitTransactionsNumber(soapTransaction.chequeDebitTransactionsNumber);

            transactions.add(dto);
        }
    }

    result.setTransactions(transactions);
    result.setTotalCount(transactions.size());

    // Convert DTOs to Entities and save to database
    List<TransactionEntity> entities = transactions.stream().map(dto -> {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(dto.getId());
        entity.setAccountNumber(dto.getAccountNumber());
        entity.setCredittransactionsAmount(dto.getCredittransactionsAmount());
        entity.setMonthlydebittransactionsAmount(dto.getMonthlydebittransactionsAmount());
        entity.setAtmTransactionsNumber(dto.getAtmTransactionsNumber());
        entity.setAtmtransactionsAmount(dto.getAtmtransactionsAmount());
        entity.setMonthlyBalance(dto.getMonthlyBalance());

        entity.setLastTransactionValue(dto.getLastTransactionValue());
        entity.setLastTransactionDate(dto.getLastTransactionDate());
        entity.setLastTransactionType(dto.getLastTransactionType());
        entity.setTransactionValue(dto.getTransactionValue());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setIntrestAmount(dto.getIntrestAmount());
        entity.setOverdraftLimit(dto.getOverdraftLimit());

        entity.setDebitcardpostransactionsAmount(dto.getDebitcardpostransactionsAmount());
        entity.setDebitcardpostransactionsNumber(dto.getDebitcardpostransactionsNumber());
        entity.setMobilemoneycredittransactionAmount(dto.getMobilemoneycredittransactionAmount());
        entity.setMobilemoneycredittransactionNumber(dto.getMobilemoneycredittransactionNumber());
        entity.setMobilemoneydebittransactionAmount(dto.getMobilemoneydebittransactionAmount());
        entity.setMobilemoneydebittransactionNumber(dto.getMobilemoneydebittransactionNumber());
        entity.setOverthecounterwithdrawalsAmount(dto.getOverthecounterwithdrawalsAmount());
        entity.setOverthecounterwithdrawalsNumber(dto.getOverthecounterwithdrawalsNumber());
        entity.setChequeDebitTransactionsAmount(dto.getChequeDebitTransactionsAmount());
        entity.setChequeDebitTransactionsNumber(dto.getChequeDebitTransactionsNumber());

        return entity;
    }).collect(Collectors.toList());

    transactionRepository.saveAll(entities);

    return result;
}

    
    // SOAP Response model classes for JAXB unmarshalling
    @XmlRootElement(name = "getTransactionResponse")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class GetTransactionResponse {
        @XmlElement(name = "transactions")
        private Transactions transactions;
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class Transactions {
        @XmlElement(name = "transactionDetails")
        private List<SoapTransactionDetails> transactionDetails;
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class SoapTransactionDetails {
        @XmlElement(name = "id")
        private Long id;
        
        @XmlElement(name = "accountNumber")
        private String accountNumber;
        
        @XmlElement(name = "credittransactionsAmount")
        private Double credittransactionsAmount;
        
        @XmlElement(name = "monthlydebittransactionsAmount")
        private Double monthlydebittransactionsAmount;
        
        @XmlElement(name = "atmTransactionsNumber")
        private Long atmTransactionsNumber;
        
        @XmlElement(name = "atmtransactionsAmount")
        private Double atmtransactionsAmount;
        
        @XmlElement(name = "monthlyBalance")
        private Double monthlyBalance;
        
        @XmlElement(name = "lastTransactionValue")
        private Double lastTransactionValue;
        
        @XmlElement(name = "lastTransactionDate")
        private Long lastTransactionDate;
        
        @XmlElement(name = "lastTransactionType")
        private String lastTransactionType;
        
        @XmlElement(name = "transactionValue")
        private Double transactionValue;
        
        @XmlElement(name = "createdAt")
        private Long createdAt;
        
        @XmlElement(name = "updatedAt")
        private Long updatedAt;
        
        @XmlElement(name = "createdDate")
        private Long createdDate;
        
        @XmlElement(name = "intrestAmount")
        private Double intrestAmount;
        
        @XmlElement(name = "overdraftLimit")
        private Double overdraftLimit;
        
        // Additional transaction fields from the XML
        @XmlElement(name = "overthecounterwithdrawalsNumber")
        private Long overthecounterwithdrawalsNumber;
        
        @XmlElement(name = "outgoinginttransactiondebitAmount")
        private Double outgoinginttransactiondebitAmount;
        
        @XmlElement(name = "outgoinginttrndebitNumber")
        private Long outgoinginttrndebitNumber;
        
        @XmlElement(name = "alternativechanneltrnsdebitNumber")
        private Long alternativechanneltrnsdebitNumber;
        
        @XmlElement(name = "incominginternationaltrncrNumber")
        private Long incominginternationaltrncrNumber;
        
        @XmlElement(name = "debitcardpostransactionsAmount")
        private Double debitcardpostransactionsAmount;
        
        @XmlElement(name = "outgoinglocaltransactiondebitNumber")
        private Long outgoinglocaltransactiondebitNumber;
        
        @XmlElement(name = "alternativechanneltrnscrNumber")
        private Long alternativechanneltrnscrNumber;
        
        @XmlElement(name = "mobilemoneycredittransactionNumber")
        private Long mobilemoneycredittransactionNumber;
        
        @XmlElement(name = "minmonthlycredittransactions")
        private Double minmonthlycredittransactions;
        
        @XmlElement(name = "maxMonthlyBebitTransactions")
        private Double maxMonthlyBebitTransactions;
        
        @XmlElement(name = "maxAtmTransactions")
        private Double maxAtmTransactions;
        
        @XmlElement(name = "incominglocaltransactioncrNumber")
        private Long incominglocaltransactioncrNumber;
        
        @XmlElement(name = "alternativechanneltrnsdebitAmount")
        private Double alternativechanneltrnsdebitAmount;
        
        @XmlElement(name = "debitcardpostransactionsNumber")
        private Long debitcardpostransactionsNumber;
        
        @XmlElement(name = "bouncedChequesDebitNumber")
        private Long bouncedChequesDebitNumber;
        
        @XmlElement(name = "fincominglocaltransactioncrAmount")
        private Double fincominglocaltransactioncrAmount;
        
        @XmlElement(name = "outgoinglocaltransactiondebitAmount")
        private Double outgoinglocaltransactiondebitAmount;
        
        @XmlElement(name = "minoverthecounterwithdrawals")
        private Double minoverthecounterwithdrawals;
        
        @XmlElement(name = "alternativechanneltrnscrAmount")
        private Double alternativechanneltrnscrAmount;
        
        @XmlElement(name = "mobilemoneycredittransactionAmount")
        private Double mobilemoneycredittransactionAmount;
        
        @XmlElement(name = "mobilemoneydebittransactionNumber")
        private Long mobilemoneydebittransactionNumber;
        
        @XmlElement(name = "incominginternationaltrncrAmount")
        private Double incominginternationaltrncrAmount;
        
        @XmlElement(name = "maxdebitcardpostransactions")
        private Double maxdebitcardpostransactions;
        
        @XmlElement(name = "minmobilemoneydebittransaction")
        private Double minmobilemoneydebittransaction;
        
        @XmlElement(name = "bouncedchequetransactionscrAmount")
        private Double bouncedchequetransactionscrAmount;
        
        @XmlElement(name = "bouncedchequetransactionsdrAmount")
        private Double bouncedchequetransactionsdrAmount;
        
        @XmlElement(name = "mobilemoneydebittransactionAmount")
        private Double mobilemoneydebittransactionAmount;
        
        @XmlElement(name = "bouncedchequescreditNumber")
        private Long bouncedchequescreditNumber;
        
        @XmlElement(name = "chequeDebitTransactionsNumber")
        private Long chequeDebitTransactionsNumber;
        
        @XmlElement(name = "chequeDebitTransactionsAmount")
        private Double chequeDebitTransactionsAmount;
        
        @XmlElement(name = "overthecounterwithdrawalsAmount")
        private Double overthecounterwithdrawalsAmount;
    }
}