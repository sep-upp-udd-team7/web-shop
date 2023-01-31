package com.baeldung.ecommerce.repository;

import com.baeldung.ecommerce.model.Product;
import com.baeldung.ecommerce.model.Subscription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    @Query("SELECT s FROM Subscription s WHERE s.token=?1")
    Subscription getSubscriptionByToken(String token);
}
