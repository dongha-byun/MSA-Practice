package com.example.userservice.vo;

import com.example.userservice.dto.UserDto;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUser {

    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email cannot be less than 2 characters")
    @Email
    private String email;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name cannot be less than 2 characters")
    private String name;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password cannot be less than 8 characters")
    private String password;

    public static UserDto toDto(RequestUser requestUser) {
        UserDto userDto = new UserDto();
        userDto.setEmail(requestUser.getEmail());
        userDto.setName(requestUser.getName());
        userDto.setPassword(requestUser.getPassword());
        return userDto;
    }
}
