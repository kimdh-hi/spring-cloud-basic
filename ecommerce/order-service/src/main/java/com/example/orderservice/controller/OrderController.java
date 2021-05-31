package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/order-service")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders/{userId}")
    public ResponseEntity<ResponseOrder> order(@PathVariable("userId") String userId, @RequestBody RequestOrder requestOrder) {
        OrderDto orderDto = new ModelMapper().map(requestOrder, OrderDto.class);
        orderDto.setUserId(userId);
        OrderDto order = orderService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ModelMapper().map(order, ResponseOrder.class));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<ResponseOrder>> getOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders()
                .stream()
                .map(o -> new ModelMapper().map(o, ResponseOrder.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ResponseOrder> getOrderByOrderId(@PathVariable("orderId") String orderId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ModelMapper().map(orderService.getOrderByOrderID(orderId), ResponseOrder.class));
    }
}
