package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public static class Config{ }

    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        /**
         * exchange: request, response 등의 정보를 얻어옴
         * chain: pre filter동작 후에 post filter를 체이닝하여 수행
         */
        // pre filter

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom pre filter: requestID -> {}", request.getId());

            // post filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Custom pre filter: responseID -> {}", response.getStatusCode());
            }));
        };
    }
}
