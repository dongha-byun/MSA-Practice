package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.GreetingVO;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RequestMapping("/user-service") // api gateway 에서 /user-service 로 된 uri 패턴을 치환해서 호출할 것이므로 공통 request uri 정보 제거
@RequiredArgsConstructor
@RestController
public class UserController {

    private final Environment env;
    private final GreetingVO greetingVO;

    private final UserService userService;

    @GetMapping("/health-check")
    public String status() {
        String port = env.getProperty("local.server.port");
        return "It's Working in User Micro Service with " + port + " port";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message");
    }

    @GetMapping("/welcome-vo")
    public String welcome_vo() {
        return greetingVO.getMessage() + " with Greeting VO";
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser requestUser) {
        UserDto user = userService.createUser(RequestUser.toDto(requestUser));
        ResponseUser responseUser = ResponseUser.of(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUserAll() {
        List<UserDto> users = userService.getUserAll();
        List<ResponseUser> userResponses = users.stream()
                .map(ResponseUser::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(userResponses);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserById(userId);
        ResponseUser responseUser = ResponseUser.of(userDto);

        return ResponseEntity.ok().body(responseUser);
    }
}
