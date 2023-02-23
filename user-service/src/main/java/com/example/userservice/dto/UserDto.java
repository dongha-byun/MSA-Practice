package com.example.userservice.dto;

import com.example.userservice.domain.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String userId;
    private String email;
    private String name;
    private String password;
    private LocalDateTime createdAt;

    private String encryptPassword;

    public static User to(UserDto userDto) {
        return new User(userDto.getUserId(), userDto.getEmail(), userDto.getName(), userDto.getEncryptPassword(), userDto.getCreatedAt());
    }

    public static UserDto of(User user) {
        return new UserDto(user.getUserId(), user.getEmail(), user.getName(), user.getEncryptPassword(), user.getCreatedAt(), user.getEncryptPassword());
    }
}
