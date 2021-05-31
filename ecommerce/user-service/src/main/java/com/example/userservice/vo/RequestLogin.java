package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class RequestLogin {

    @NotNull(message = "Email can't not be empty.")
    @Email
    private String email;

    @NotNull(message = "Password can't not be empty.")
    private String password;
}
