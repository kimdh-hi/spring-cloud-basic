package com.example.userservice.controller;

import com.example.userservice.vo.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {

    private final Environment env;

    private final Greeting greeting;


    @GetMapping("/check")
    public String check() {
        return "Good!!";
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
