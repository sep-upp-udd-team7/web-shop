package com.baeldung.ecommerce.service;

import com.baeldung.ecommerce.dto.AccessTokenDto;
import com.baeldung.ecommerce.dto.AccessTokenRequestDto;
import com.baeldung.ecommerce.dto.Token;
import com.baeldung.ecommerce.dto.TransactionDataDto;
import com.baeldung.ecommerce.model.Order;
import com.baeldung.ecommerce.model.OrderStatus;
import com.baeldung.ecommerce.repository.OrderRepository;
import com.baeldung.ecommerce.utils.RandomCharacterGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    @Value("${shop-auth.client-id}")
    private String shopId;

    @Value("${shop-auth.client-secret}")
    private String clientSecret;


    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Iterable<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    @Override
    public Order create(Order order) {
        order.setDateCreated(LocalDate.now());
        order.setStatus(OrderStatus.CREATED);
        order.setOrderId(RandomCharacterGenerator.generateURLSafeString(16));
        return this.orderRepository.save(order);
    }

    public Token generateOrderToken(Order order) {
        WebClient webClient = WebClient.create("http://localhost:8000");
        TransactionDataDto transactionDataDto = new TransactionDataDto();
        transactionDataDto.setTransactionId(order.getOrderId());
        transactionDataDto.setShopId(shopId);
        System.out.println(order.getTotalOrderPrice());
        transactionDataDto.setAmount(order.getTotalOrderPrice().toString());
        String accessToken=generateAccessToken();
        transactionDataDto.setAccessToken(accessToken);

        Token token = webClient.post()
                .uri("/auth-service/generate-url-token")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+accessToken)
                .body(Mono.just(transactionDataDto), TransactionDataDto.class)
                .retrieve()
                .bodyToMono(Token.class)
                .block();

        return token;
    }

    private String generateAccessToken(){
        AccessTokenRequestDto dto=new AccessTokenRequestDto();
        dto.setClientId(shopId);
        dto.setClientSecret(clientSecret);
        WebClient webClient = WebClient.create("http://localhost:8000");
        AccessTokenDto accessTokenDto = webClient.post()
                .uri("/auth-service/generate-jwt")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(dto), AccessTokenRequestDto.class)
                .retrieve()
                .bodyToMono(AccessTokenDto.class)
                .block();
        return accessTokenDto.getToken();
    }

    @Override
    public Order changeOrderState(String orderId, OrderStatus status) {
        Order order=orderRepository.getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        this.orderRepository.save(order);
    }
}
