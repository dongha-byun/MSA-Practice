package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.domain.User;
import com.example.userservice.domain.UserRepository;
import com.example.userservice.dto.UserDto;
import com.example.userservice.error.FeignErrorDecoder;
import com.example.userservice.vo.ResponseOrder;
import feign.FeignException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final Environment env;
    private final RestTemplate restTemplate;

    private final OrderServiceClient orderServiceClient;
    private final FeignErrorDecoder errorDecoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User findUser = userRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(username)
                );

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
        UserDto userDto = UserDto.of(user);

        /* using restTemplate */
//        String orderUri = String.format(env.getProperty("order-service.url"), userId);
//        ResponseEntity<List<ResponseOrder>> response =
//                restTemplate.exchange(orderUri, HttpMethod.GET, null,
//                        new ParameterizedTypeReference<List<ResponseOrder>>() {
//                });
//        List<ResponseOrder> orders = response.getBody();

        /* using feign client */
//        try{
//            List<ResponseOrder> orders = orderServiceClient.getOrders(userId);
//            userDto.setOrders(orders);
//        }catch(FeignException e) {
//            log.error(e.getMessage());
//        }

        /* error handling by error decoder */
        List<ResponseOrder> orders = orderServiceClient.getOrders(userId);
        userDto.setOrders(orders);
        return userDto;
    }

    @Override
    public List<UserDto> getUserAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserDetailsByEmail(String userName) {
        User user = userRepository.findByEmail(userName)
                .orElseThrow(
                        () -> new UsernameNotFoundException("사용자 조회 불가")
                );

        return UserDto.of(user);
    }
}
