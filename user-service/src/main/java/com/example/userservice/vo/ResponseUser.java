package com.example.userservice.vo;

import com.example.userservice.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ResponseUser {

    private String userId;
    private String email;
    private String name;

    private List<ResponseOrder> orders;

    public static ResponseUser of(UserDto userDto) {
        return new ResponseUser(userDto.getUserId(), userDto.getEmail(), userDto.getName(), null);
    }
}
