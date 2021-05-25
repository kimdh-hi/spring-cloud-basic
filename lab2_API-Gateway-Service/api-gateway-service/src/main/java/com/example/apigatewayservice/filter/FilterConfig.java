package com.example.apigatewayservice.filter;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class FilterConfig {

    /**
     * 외부설정파일(.yml,.properties)가 아닌 Java코드로 라우팅 및 필터 설정정보를 빈으로 등록
     * 필터는 단순히 요청헤더와 응답헤더를 추가
     * 각 서비스에서 추가된 헤더를 확인하여 필터 적용 여부를 체크
     */
//    @Bean
    public RouteLocator gatewayRoutingFilteringConfig(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/first-service/**")
                            .filters(f -> f.addRequestHeader("Test-req-Header","Test")
                                           .addResponseHeader("Test-res-Header","Test"))
                            .uri("http://localhost:8081/"))
                .route(r -> r.path("/second-service/**")
                        .filters(f -> f.addRequestHeader("Test-req-Header","Test")
                                .addResponseHeader("Test-res-Header","Test"))
                        .uri("http://localhost:8082/"))
                .build();
    }
}
