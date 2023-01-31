package com.baeldung.ecommerce.service;

import com.baeldung.ecommerce.dto.*;
import com.baeldung.ecommerce.model.OrderStatus;
import com.baeldung.ecommerce.model.Subscription;
import com.baeldung.ecommerce.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SubscriptionService {

    @Autowired
    private Environment environment;

    @Value("${shop-auth.client-id}")
    private String shopId;

    @Value("${shop-auth.client-secret}")
    private String clientSecret;

    private SubscriptionRepository subscriptionRepository;
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }


    public PaypalRedirectUrlDto createSubscription(String price) {
        Subscription subscription=new Subscription();
        subscription.setPrice(price);
        subscription.setStatus(OrderStatus.CREATED);
        subscription=subscriptionRepository.save(subscription);
        String accessToken=generateAccessToken();

        WebClient webClient = WebClient.create(environment.getProperty("psp.url"));
        CreatePaypalSubscriptionDto dto=new CreatePaypalSubscriptionDto();
        dto.setPrice(price);
        PaypalRedirectUrlDto redirectUrlDto = webClient.post()
                .uri("/paypal-service/subscriptions/create")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+accessToken)
                .body(Mono.just(dto), CreatePaypalSubscriptionDto.class)
                .retrieve()
                .bodyToMono(PaypalRedirectUrlDto.class)
                .block();
        subscription.setToken(redirectUrlDto.getToken());
        subscriptionRepository.save(subscription);
        return redirectUrlDto;

    }


    private String generateAccessToken(){
        AccessTokenRequestDto dto=new AccessTokenRequestDto();
        dto.setClientId(shopId);
        dto.setClientSecret(clientSecret);
        WebClient webClient = WebClient.create(environment.getProperty("psp.url"));
        AccessTokenDto accessTokenDto = webClient.post()
                .uri("/auth-service/generate-jwt")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(dto), AccessTokenRequestDto.class)
                .retrieve()
                .bodyToMono(AccessTokenDto.class)
                .block();
        return accessTokenDto.getToken();
    }

    public boolean confirmSubscription(String token){
        boolean retVal=false;
        Subscription subscription=subscriptionRepository.getSubscriptionByToken(token);
        if (subscription!=null){
            subscription.setStatus(OrderStatus.PAID);
            subscriptionRepository.save(subscription);
            retVal=true;
        }
        return retVal;

    }

    public boolean cancelSubscription(String token){
        boolean retVal=false;
        Subscription subscription=subscriptionRepository.getSubscriptionByToken(token);
        if (subscription!=null){
            subscription.setStatus(OrderStatus.CANCELED);
            subscriptionRepository.save(subscription);
            retVal=true;
        }
        return retVal;

    }



}
