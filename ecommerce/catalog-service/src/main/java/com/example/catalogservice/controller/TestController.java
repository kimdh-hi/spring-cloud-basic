package com.example.catalogservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/catalog-service")
public class TestController {

    private final Environment env;

    @GetMapping("/port-check")
    public String check() {
        return String.format("UserService Port : %s", env.getProperty("local.server.port"));
    }
}
