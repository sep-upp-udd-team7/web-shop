package com.baeldung.ecommerce.controller;

import com.baeldung.ecommerce.dto.CreateOrderResponseDto;
import com.baeldung.ecommerce.dto.OrderDetailDto;
import com.baeldung.ecommerce.dto.OrderProductDto;
import com.baeldung.ecommerce.dto.Token;
import com.baeldung.ecommerce.exception.ResourceNotFoundException;
import com.baeldung.ecommerce.model.Order;
import com.baeldung.ecommerce.model.OrderProduct;
import com.baeldung.ecommerce.model.OrderStatus;
import com.baeldung.ecommerce.service.OrderProductService;
import com.baeldung.ecommerce.service.OrderService;
import com.baeldung.ecommerce.service.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    ProductService productService;
    OrderService orderService;
    OrderProductService orderProductService;

    public OrderController(ProductService productService, OrderService orderService, OrderProductService orderProductService) {
        this.productService = productService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @NotNull Iterable<Order> list() {
        return this.orderService.getAllOrders();
    }

    @GetMapping("/confirm/{orderId}")
    public ResponseEntity<?> confirmOrder(@PathVariable String orderId){
        Order order=this.orderService.changeOrderState(orderId, OrderStatus.PAID);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable String orderId){
        Order order=this.orderService.changeOrderState(orderId,OrderStatus.CANCELED);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("{orderId}")
    public ResponseEntity<?> getOrderDetail(@PathVariable String orderId){
        OrderDetailDto orderDetailDto = orderService.getOrderDetail(orderId);
        return new ResponseEntity<>(orderDetailDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponseDto> create(@RequestBody OrderForm form) {
        List<OrderProductDto> formDtos = form.getProductOrders();
        validateProductsExistence(formDtos);
        Order order = new Order();
        order = this.orderService.create(order);

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductDto dto : formDtos) {
            orderProducts.add(orderProductService.create(new OrderProduct(order, productService.getProduct(dto
              .getProduct()
              .getId()), dto.getQuantity())));
        }

        order.setOrderProducts(orderProducts);
        order.setPrice(order.getTotalOrderPrice());

        this.orderService.update(order);

        Token token=this.orderService.generateOrderToken(order);

        String uri = ServletUriComponentsBuilder
          .fromCurrentServletMapping()
          .path("/orders/{id}")
          .buildAndExpand(order.getId())
          .toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);

        CreateOrderResponseDto dto=new CreateOrderResponseDto();
        dto.setOrder(order);
        dto.setToken(token);

        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
    }

    private void validateProductsExistence(List<OrderProductDto> orderProducts) {
        List<OrderProductDto> list = orderProducts
          .stream()
          .filter(op -> Objects.isNull(productService.getProduct(op
            .getProduct()
            .getId())))
          .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(list)) {
            new ResourceNotFoundException("Product not found");
        }
    }

    public static class OrderForm {

        private List<OrderProductDto> productOrders;

        public List<OrderProductDto> getProductOrders() {
            return productOrders;
        }

        public void setProductOrders(List<OrderProductDto> productOrders) {
            this.productOrders = productOrders;
        }
    }
}
