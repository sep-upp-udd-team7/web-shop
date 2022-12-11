package com.baeldung.ecommerce.repository;

import com.baeldung.ecommerce.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query("SELECT o FROM Order o where o.orderId=?1")
    Order getOrderById(String orderId);
}
