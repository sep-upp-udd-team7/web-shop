package com.baeldung.ecommerce.dto;

import com.baeldung.ecommerce.model.Product;

import java.util.ArrayList;

public class OrderDetailDto {
    private String status;
    private String orderId;
    private double price;
    private ArrayList<OrderProductDto> productOrders;

    public OrderDetailDto(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<OrderProductDto> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(ArrayList<OrderProductDto> productOrders) {
        this.productOrders = productOrders;
    }

}
