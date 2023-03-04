package com.example.userservice.dto;

import com.example.userservice.domain.User;
import com.example.userservice.vo.ResponseOrder;
import java.time.LocalDateTime;
import java.util.List;
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
    private List<ResponseOrder> orders;

    public UserDto(String userId, String email, String name, String password, LocalDateTime createdAt,
                   String encryptPassword) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.createdAt = createdAt;
        this.encryptPassword = encryptPassword;
    }

    public static User to(UserDto userDto) {
        return new User(userDto.getUserId(), userDto.getEmail(), userDto.getName(), userDto.getEncryptPassword(), userDto.getCreatedAt());
    }

    public static UserDto of(User user) {
        return new UserDto(user.getUserId(), user.getEmail(), user.getName(), user.getEncryptPassword(), user.getCreatedAt(), user.getEncryptPassword());
    }
}
