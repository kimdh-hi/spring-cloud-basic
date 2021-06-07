package com.example.orderservice.dto;

import jdk.jfr.DataAmount;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDto implements Serializable {

    private String productId;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
