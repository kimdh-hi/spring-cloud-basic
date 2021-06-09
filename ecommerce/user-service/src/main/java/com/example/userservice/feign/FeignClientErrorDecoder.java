package com.example.userservice.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignClientErrorDecoder implements ErrorDecoder {

    /**
     * FeignClient 호출에 대한 예외처리 이 예외처리를 해주지 않으면 예외 발생시 FeignClient를 호출한
     * 서비스를 기준으로 서버오류(5xx)가 발생하게 됨
     * 호출된 서비스에서 발생한 예외를 따로 처리해주어야 함
     */
    @Override
    public Exception decode(String method, Response response) {

        switch (response.status()) {
            case 404: 
                if(method.contains("getOrders")) {
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()), "User's order list is empty.");
                }
                break;
            default:
                return new Exception(response.reason());
        }
        return null;
    }
}
