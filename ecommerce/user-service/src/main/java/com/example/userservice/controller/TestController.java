package com.example.userservice.controller;

import com.example.userservice.vo.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/user-service")
@RequestMapping("/")
public class TestController {

    private final Environment env;

    private final Greeting greeting;


    @GetMapping("/port-check")
    public String check() {
        return String.format("UserService Port : %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome1")
    public String welcome1() {
        return env.getProperty("greeting.message");
    }

    @GetMapping("/welcome2")
    public String welcome2() {
        return greeting.getMessage();
    }
}
