package com.example.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {

    private String productId;
    private String orderId;
    private Integer quantity;
    private Integer price;
    private Integer totalPrice;

    private Date createdAt;

}
