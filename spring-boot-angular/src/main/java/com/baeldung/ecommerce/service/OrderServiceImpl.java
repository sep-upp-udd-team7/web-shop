package com.baeldung.ecommerce.service;

import com.baeldung.ecommerce.dto.*;
import com.baeldung.ecommerce.model.Order;
import com.baeldung.ecommerce.model.OrderProduct;
import com.baeldung.ecommerce.model.OrderStatus;
import com.baeldung.ecommerce.repository.OrderProductRepository;
import com.baeldung.ecommerce.repository.OrderRepository;
import com.baeldung.ecommerce.utils.RandomCharacterGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    @Value("${shop-auth.client-id}")
    private String shopId;

    @Value("${shop-auth.client-secret}")
    private String clientSecret;

    @Autowired
    private Environment environment;

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
        System.out.println("URL PSP: " + environment.getProperty("psp.url"));
        WebClient webClient = WebClient.create(environment.getProperty("psp.url"));
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
        System.out.println("URL PSP: " + environment.getProperty("psp.url"));
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

    @Override
    public Order changeOrderState(String orderId, OrderStatus status) {
        Order order=orderRepository.getOrderById(orderId);
        if(order == null) return null;
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Override
    public OrderDetailDto getOrderDetail(String orderId) {
        Order order = orderRepository.getOrderById(orderId);
        if(order == null) return null;
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setOrderId(orderId);
        orderDetailDto.setStatus(order.getStatus().toString());
        orderDetailDto.setPrice(order.getPrice());
        ArrayList<OrderProductDto> orderProductDtos = new ArrayList<>();
        for(OrderProduct orderProduct : order.getOrderProducts()){
            orderProductDtos.add(new OrderProductDto(orderProduct.getProduct(), orderProduct.getQuantity()));
        }
        orderDetailDto.setProductOrders(orderProductDtos);
        return orderDetailDto;
    }

    @Override
    public void update(Order order) {
        this.orderRepository.save(order);
    }
}
