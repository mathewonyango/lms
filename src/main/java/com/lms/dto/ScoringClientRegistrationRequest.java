// ScoringClientRegistrationRequest.java

package com.lms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScoringClientRegistrationRequest {
    private String url;
    private String name;
    private String username;
    private String password;
}