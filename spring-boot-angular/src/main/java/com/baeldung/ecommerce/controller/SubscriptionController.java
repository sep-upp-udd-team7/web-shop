package com.baeldung.ecommerce.controller;

import com.baeldung.ecommerce.dto.PaypalRedirectUrlDto;
import com.baeldung.ecommerce.service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService){
        this.subscriptionService=subscriptionService;
    }

    @GetMapping("/{price}")
    public ResponseEntity<PaypalRedirectUrlDto> createSubscription(@PathVariable String price){
        return new ResponseEntity<>(subscriptionService.createSubscription(price), HttpStatus.OK);
    }

    @GetMapping("/confirm/{token}")
    public ResponseEntity<?> confirmSubscription(@PathVariable String token){
        boolean ret= subscriptionService.confirmSubscription(token);
        if (ret){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cancel/{token}")
    public ResponseEntity<?> cancelSubscription(@PathVariable String token){
        boolean ret= subscriptionService.cancelSubscription(token);
        if (ret){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
