package com.lms.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;
@Component

public class SoapClientConfig {

     @Value("${cbs.rest.base-url}")
    private String baseUrl; // Should be: http://16.170.239.185:8099
    
    private final RestTemplate restTemplate;
    
    public SoapClientConfig(RestTemplate restTemplate) {
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
}
