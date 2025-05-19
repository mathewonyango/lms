package com.lms.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class KycClient {

    private final String baseUrl;
    private final RestTemplate restTemplate;

    public KycClient(@Value("${kyc.service.base-url}") String baseUrl, RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    public String getKycDetails(String customerNumber) {
        try {
            String url = baseUrl + "/kyc/customer-details";
            String requestXml = buildKycSoapRequest(customerNumber);

            // Correct headers for SOAP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            headers.setAccept(List.of(MediaType.APPLICATION_XML));

            HttpEntity<String> request = new HttpEntity<>(requestXml, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to call KYC SOAP service for customer: " + customerNumber, e);
        }
    }

    private String buildKycSoapRequest(String customerNumber) {
        return String.format("""
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
                <soapenv:Header/>
                <soapenv:Body>
                    <getCustomerRequest>
                        <customerNumber>%s</customerNumber>
                    </getCustomerRequest>
                </soapenv:Body>
            </soapenv:Envelope>
        """, customerNumber);
    }
}
