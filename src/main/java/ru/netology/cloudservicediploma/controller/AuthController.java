package ru.netology.cloudservicediploma.controller;

import ru.netology.cloudservicediploma.entity.dto.AuthRequestDto;
import ru.netology.cloudservicediploma.entity.dto.AuthResponseDto;
import ru.netology.cloudservicediploma.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authUser(@RequestBody @Validated AuthRequestDto authRequestDto) {
        System.out.println(authService);
        return ResponseEntity.ok(authService.loginUser(authRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader("auth-token") String authToken) {
        authService.logoutUser(authToken);
        return ResponseEntity.ok().build();
    }
}
