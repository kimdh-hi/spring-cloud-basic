package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<ResponseOrder>> getOrderByOrderId(@PathVariable("userId") String userId) {
        List<OrderDto> findOrders = orderService.getOrderByUserId(userId);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<ResponseOrder> result = new ArrayList<>();
        findOrders.forEach( o -> {
            result.add(mapper.map(o, ResponseOrder.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
