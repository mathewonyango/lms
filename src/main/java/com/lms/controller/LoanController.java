package com.lms.controller;

import com.lms.dto.ApiResponse;
import com.lms.dto.LoanRequestDTO;
import com.lms.dto.LoanResponseDTO;

import com.lms.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<LoanResponseDTO>> applyForLoan(@RequestBody LoanRequestDTO request) {
        if (request.getCustomerNumber() == null || request.getRequestedAmount() == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Customer number and amount are required", null));
        }

        LoanResponseDTO response = loanService.applyForLoan(request);

        return ResponseEntity.ok(new ApiResponse<>(true, response.getMessage(), response));
    }
}