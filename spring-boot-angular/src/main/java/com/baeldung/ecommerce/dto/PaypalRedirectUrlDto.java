package com.baeldung.ecommerce.dto;

import lombok.Data;

@Data
public class PaypalRedirectUrlDto {
    String url;

    String token;

}