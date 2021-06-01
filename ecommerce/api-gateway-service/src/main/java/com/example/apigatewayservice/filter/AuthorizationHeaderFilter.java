package com.example.apigatewayservice.filter;

import com.netflix.discovery.converters.Auto;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    public static class Config { }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> { // pre-filter
            ServerHttpRequest request = exchange.getRequest(); // 인증 request

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) // AUTHORIZATION 헤더 확인
                return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);

            // AUTHORIZATION헤더 내 JWT값 가져오기
            String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authHeader.replace("Bearer", "");

            if (!validateJwt(jwt)) // JWT validation
                return onError(exchange, "JWT is not valid", HttpStatus.UNAUTHORIZED);

            return chain.filter(exchange); // post-filter
        };
    }

    private boolean validateJwt(String jwt) {
        boolean result = true; // JWT 유효 여부
        String subject = null; // JWT subject

        try {
            subject = Jwts.parser() // JWT 파싱
                    .setSigningKey(env.getProperty("token.secret")) // JWT 생성시 사용한 secret 값
                    .parseClaimsJws(jwt).getBody() // 복호화 대상
                    .getSubject();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }

        if(subject == null) result = false;

        return result;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse(); // response header 세팅
        response.setStatusCode(status); // 상태코드
        log.error(message);
        return response.setComplete();
    }
}
