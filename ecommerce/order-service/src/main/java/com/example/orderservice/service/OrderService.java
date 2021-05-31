package com.example.orderservice.service;

import com.example.orderservice.domain.OrderEntity;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getPrice() * orderDto.getQuantity());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderEntity orderEntity = mapper.map(orderDto, OrderEntity.class);
        orderRepository.save(orderEntity);
        return mapper.map(orderEntity, OrderDto.class);
    }

    public List<OrderDto> getAllOrders() {
        List<OrderDto> result = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        orderRepository.findAll().forEach(o -> {
            result.add(mapper.map(o, OrderDto.class));
        });
        return result;
    }

    public OrderDto getOrderByOrderID(String orderId) {
        return new ModelMapper().map(orderRepository.findByOrderId(orderId), OrderDto.class);
    }


    public List<OrderDto> getOrderByUserId(String userId) {
        List<OrderDto> result = new ArrayList<>();
        orderRepository.findByUserId(userId).forEach(o -> {
            result.add(new ModelMapper().map(o, OrderDto.class));
        });
        return result;
    }

}
