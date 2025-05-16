package com.lms.config;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@Configuration
public class SoapClientConfig {

   @Value("${soap.client.kyc.url:http://localhost:8080/kyc}")
private String kycServiceUrl;

@Value("${soap.client.transaction.url:http://localhost:8080/transaction}")
private String transactionServiceUrl;

@Value("${soap.client.kyc.username:admin}")
private String kycUsername;

@Value("${soap.client.kyc.password:password123}")
private String kycPassword;

@Value("${soap.client.transaction.username:admin}")
private String transactionUsername;

@Value("${soap.client.transaction.password:password123}")
private String transactionPassword;
    @Value("${soap.client.kyc.timeout:30}")
    private int kycTimeout;

    @Bean
    public Jaxb2Marshaller customerMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.credable.credable.soap.generated");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate customerWebServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(customerMarshaller());
        webServiceTemplate.setUnmarshaller(customerMarshaller());
        webServiceTemplate.setDefaultUri(kycServiceUrl);
        webServiceTemplate.setMessageSender(httpComponentsMessageSender());
        return webServiceTemplate;
    }

    @Bean
    public Jaxb2Marshaller transactionMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.credable.credable.soap.generated");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate transactionWebServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(transactionMarshaller());
        webServiceTemplate.setUnmarshaller(transactionMarshaller());
        webServiceTemplate.setDefaultUri(transactionServiceUrl);
        webServiceTemplate.setMessageSender(httpComponentsMessageSender());
        return webServiceTemplate;
    }

    @Bean
    public HttpComponentsMessageSender httpComponentsMessageSender() {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        httpComponentsMessageSender.setConnectionTimeout((int) Duration.ofSeconds(30).toMillis());
        httpComponentsMessageSender.setReadTimeout((int) Duration.ofSeconds(30).toMillis());
        return httpComponentsMessageSender;
    }

  

}
