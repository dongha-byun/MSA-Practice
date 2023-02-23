package com.example.userservice.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestLogin {

    @NotNull(message = "email can not be null!")
    @Size(min = 2, message = "email not be less than two character")
    private String email;

    @NotNull(message = "password can not be null!")
    @Size(min = 8, message = "password not be greater than two character")
    private String password;
}
