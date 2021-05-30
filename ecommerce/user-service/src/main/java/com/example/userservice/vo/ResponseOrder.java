package com.example.userservice.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseOrder {

    private Long productId;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;
    private Date CreateAt;
    private String orderId;
}
