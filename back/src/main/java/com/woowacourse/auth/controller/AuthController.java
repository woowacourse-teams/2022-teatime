package com.woowacourse.auth.controller;

import com.woowacourse.auth.controller.dto.TokenResponse;
import com.woowacourse.auth.service.AuthService;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @NotNull String code) {
        TokenResponse response = authService.login(code);
        return ResponseEntity.ok(response);
    }
}
