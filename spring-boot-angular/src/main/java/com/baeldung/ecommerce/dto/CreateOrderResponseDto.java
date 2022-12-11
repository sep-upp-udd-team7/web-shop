package com.baeldung.ecommerce.dto;

import com.baeldung.ecommerce.model.Order;
import lombok.Data;

@Data
public class CreateOrderResponseDto {

    private Order order;
    private Token token;
}
