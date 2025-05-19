package com.lms.service;

import com.lms.dto.SubscriptionRequest;
import com.lms.dto.SubscriptionResponse;

import jakarta.xml.bind.JAXBException;

public interface SubscriptionService {
    SubscriptionResponse subscribeCustomer(SubscriptionRequest request);
}
