package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUserById(String userId);
    List<UserDto> getUserAll();
}
