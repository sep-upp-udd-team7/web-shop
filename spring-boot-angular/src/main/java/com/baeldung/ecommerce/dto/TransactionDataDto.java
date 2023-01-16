package com.baeldung.ecommerce.dto;

import lombok.Data;

@Data
public class TransactionDataDto {

    private String shopId;

    private String amount;

    private String transactionId;

    private String accessToken;
}

