package com.lms.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatusHistory {
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    
    @JsonProperty("comment")
    private String comment;
    
    @JsonProperty("updatedBy")
    private String updatedBy;
}
