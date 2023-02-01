package com.baeldung.ecommerce.dto;

import lombok.Data;

@Data
public class AccessTokenDto {
    String token;

    String roles;
}
