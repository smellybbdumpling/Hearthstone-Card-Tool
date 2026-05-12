package com.example.hearthstone.service;

import com.example.hearthstone.dto.LoginRequest;
import com.example.hearthstone.dto.RegisterRequest;
import com.example.hearthstone.security.AuthUser;
import com.example.hearthstone.vo.LoginVO;
import com.example.hearthstone.vo.UserVO;

public interface AuthService {
    UserVO register(RegisterRequest request);
    LoginVO login(LoginRequest request);
    UserVO me(AuthUser authUser);
}
