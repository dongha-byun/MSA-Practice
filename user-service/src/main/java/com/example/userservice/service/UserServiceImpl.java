package com.example.userservice.service;

import com.example.userservice.domain.User;
import com.example.userservice.domain.UserRepository;
import com.example.userservice.dto.UserDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("In >>>>> UserServiceImpl.loadUserByUsername");

        User findUser = userRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(username)
                );

        System.out.println("Out <<<<<< UserServiceImpl.loadUserByUsername");
        return new org.springframework.security.core.userdetails.User(
                findUser.getEmail(), findUser.getEncryptPassword(),
                true, true, true, true,
                new ArrayList<>());
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setCreatedAt(LocalDateTime.now());
        userDto.setEncryptPassword(encoder.encode(userDto.getPassword()));

        User user = UserDto.to(userDto);
        userRepository.save(user);

        return userDto;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new IllegalArgumentException("사용자 조회 불가")
                );
        return UserDto.of(user);
    }

    @Override
    public List<UserDto> getUserAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDto::of)
                .collect(Collectors.toList());
    }
}
