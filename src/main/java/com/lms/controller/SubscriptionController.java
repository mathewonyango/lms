package com.lms.controller;

import com.lms.dto.ApiResponse;
import com.lms.dto.SubscriptionRequest;
import com.lms.dto.SubscriptionResponse;
import com.lms.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubscriptionResponse>> subscribe(@RequestBody SubscriptionRequest request) {
        if (request.getCustomerNumber() == null || request.getCustomerNumber().isBlank()) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, "Customer number is required", null)
            );
        }

        SubscriptionResponse response = subscriptionService.subscribeCustomer(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Subscription processed", response));
    }
}