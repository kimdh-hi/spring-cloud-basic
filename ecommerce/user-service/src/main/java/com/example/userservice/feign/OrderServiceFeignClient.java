package com.example.userservice.feign;

import com.example.userservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service") // 호출하고자 하는 api의 MSA이름
public interface OrderServiceFeignClient {

    @GetMapping("/order-service/orderss/{userId}")
    List<ResponseOrder> getOrders(@PathVariable String userId);

    @GetMapping("/order-service/orders-error/{userId}")
    List<ResponseOrder> getOrdersError(@PathVariable String userId);
}
