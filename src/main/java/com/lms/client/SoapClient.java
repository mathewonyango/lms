package com.lms.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class SoapClient {
    
       @Value("${cbs.rest.base-url}")
    private String baseUrl; // Should be: http://16.170.239.185:8099
    
    private final RestTemplate restTemplate;
    
    public SoapClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public String getTransactions(String customerNumber) {
        try {
            String url = baseUrl + "/api/v1/cbs/transactions/" + customerNumber;
            
            // Make REST call - this matches your curl command
            String response = restTemplate.getForObject(url, String.class);
            
            return response;
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to call CBS REST service for customer: " + customerNumber, e);
        }
    }

    
    private String buildSoapRequest(String customerNumber) {
        return String.format(
            """
            <?xml version="1.0" encoding="UTF-8"?>
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" 
                           xmlns:cbs="http://cbs.example.com/transactions">
                <soap:Header/>
                <soap:Body>
                    <cbs:GetTransactionsRequest>
                        <cbs:customerNumber>%s</cbs:customerNumber>
                    </cbs:GetTransactionsRequest>
                </soap:Body>
            </soap:Envelope>
            """, customerNumber
        );
    }
}