package com.baeldung.ecommerce.dto;

import lombok.Data;

@Data
public class AccessTokenRequestDto {
    private String clientId;
    private String clientSecret;
}
