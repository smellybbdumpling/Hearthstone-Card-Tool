package com.example.hearthstone.controller;

import com.example.hearthstone.common.ApiResponse;
import com.example.hearthstone.dto.LoginRequest;
import com.example.hearthstone.dto.RegisterRequest;
import com.example.hearthstone.security.AuthUser;
import com.example.hearthstone.service.AuthService;
import com.example.hearthstone.vo.LoginVO;
import com.example.hearthstone.vo.UserVO;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<UserVO> register(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<LoginVO> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ApiResponse<UserVO> me(@AuthenticationPrincipal AuthUser authUser) {
        return ApiResponse.ok(authService.me(authUser));
    }
}
