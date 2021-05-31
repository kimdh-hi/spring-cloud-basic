package com.example.orderservice.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseOrder {

    private String orderId;
    private String productId;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;

    private Date createdAt;

}
