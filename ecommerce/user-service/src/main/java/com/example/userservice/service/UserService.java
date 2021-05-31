package com.example.userservice.service;

import com.example.userservice.domain.UserEntity;
import com.example.userservice.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public Long createUser(UserDto userDto);

    public UserDto getUserByUserId(String userId);

    public Iterable<UserEntity> getAllUsers();

    UserDto getUserDetailsByUsername(String username);
    
}
