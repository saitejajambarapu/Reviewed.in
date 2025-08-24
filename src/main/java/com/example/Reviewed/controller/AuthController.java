package com.example.Reviewed.controller;

import com.example.Reviewed.Dto.LoginDto;
import com.example.Reviewed.Dto.LoginResponseDto;
import com.example.Reviewed.Dto.SignUpDto;
import com.example.Reviewed.Dto.UserDto;
import com.example.Reviewed.serviceimpl.AuthServiceImpl;
import com.example.Reviewed.serviceimpl.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserServiceImpl userService;
    private final AuthServiceImpl authService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {
        UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletRequest request,
                                                  HttpServletResponse response) {
        LoginResponseDto loginResponseDto = authService.login(loginDto);

        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(@RequestBody Map<String, String> refreshToken) {
//        String refreshToken = Arrays.stream(request.getCookies()).
//                filter(cookie -> "refreshToken".equals(cookie.getName()))
//                .findFirst()
//                .map(Cookie::getValue)
//                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));
        String token = refreshToken.get("refreshToken");
        LoginResponseDto loginResponseDto = authService.refreshToken(token);

        return ResponseEntity.ok(loginResponseDto);
    }
}
