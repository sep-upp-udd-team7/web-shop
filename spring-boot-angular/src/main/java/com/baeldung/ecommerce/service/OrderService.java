package com.baeldung.ecommerce.service;

import com.baeldung.ecommerce.dto.OrderDetailDto;
import com.baeldung.ecommerce.dto.Token;
import com.baeldung.ecommerce.model.Order;
import com.baeldung.ecommerce.model.OrderStatus;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface OrderService {

    @NotNull Iterable<Order> getAllOrders();

    Order create(@NotNull(message = "The order cannot be null.") @Valid Order order);

    void update(@NotNull(message = "The order cannot be null.") @Valid Order order);

    Token generateOrderToken(Order order);

    Order changeOrderState(String orderId, OrderStatus status);

    OrderDetailDto getOrderDetail(String orderId);
}
