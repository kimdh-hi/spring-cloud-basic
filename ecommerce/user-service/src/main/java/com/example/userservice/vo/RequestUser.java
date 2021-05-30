package com.example.userservice.vo;

import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {

    @NotNull(message = "Email cannot be empty")
    private String email;

    @NotNull(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be greater than 8 characters")
    private String password;
}
