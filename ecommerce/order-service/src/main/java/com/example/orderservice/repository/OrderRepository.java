package com.example.orderservice.repository;

import com.example.orderservice.domain.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface

OrderRepository extends CrudRepository<OrderEntity, Long> {

    OrderEntity findByOrderId(String orderId);
    Iterable<OrderEntity> findByUserId(String userId);
}
