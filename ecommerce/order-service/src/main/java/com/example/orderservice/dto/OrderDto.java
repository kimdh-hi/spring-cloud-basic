package com.example.orderservice.dto;

import jdk.jfr.DataAmount;
import lombok.Data;

@Data
public class OrderDto {

    private String productId;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
