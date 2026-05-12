package com.example.hearthstone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.hearthstone.dto.LoginRequest;
import com.example.hearthstone.dto.RegisterRequest;
import com.example.hearthstone.entity.User;
import com.example.hearthstone.exception.BusinessException;
import com.example.hearthstone.mapper.UserMapper;
import com.example.hearthstone.security.AuthUser;
import com.example.hearthstone.security.JwtTokenProvider;
import com.example.hearthstone.service.AuthService;
import com.example.hearthstone.vo.LoginVO;
import com.example.hearthstone.vo.UserVO;
import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public UserVO register(RegisterRequest request) {
        Long count = userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getUsername, request.username()));
        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setNickname(request.nickname());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.insert(user);
        return toVO(user);
    }

    @Override
    public LoginVO login(LoginRequest request) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, request.username()));
        if (user == null || !passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        return new LoginVO(tokenProvider.createToken(user.getId(), user.getUsername()), toVO(user));
    }

    @Override
    public UserVO me(AuthUser authUser) {
        User user = userMapper.selectById(authUser.id());
        if (user == null) {
            throw new BusinessException(401, "用户不存在");
        }
        return toVO(user);
    }

    private UserVO toVO(User user) {
        return new UserVO(user.getId(), user.getUsername(), user.displayName());
    }
}
