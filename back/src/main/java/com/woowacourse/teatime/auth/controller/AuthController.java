package com.woowacourse.teatime.auth.controller;

import com.woowacourse.teatime.auth.controller.dto.LoginRequest;
import com.woowacourse.teatime.auth.controller.dto.LoginResponse;
import com.woowacourse.teatime.auth.controller.dto.UserAuthDto;
import com.woowacourse.teatime.auth.infrastructure.ResponseCookieTokenProvider;
import com.woowacourse.teatime.auth.service.AuthService;
import com.woowacourse.teatime.auth.service.LoginService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    private final LoginService loginService;
    private final ResponseCookieTokenProvider cookieTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/v2")
    public ResponseEntity<LoginResponse> loginV2(@Valid @RequestBody LoginRequest request,
                                                 HttpServletResponse response) {
        UserAuthDto userAuthDto = loginService.login(request);
        cookieTokenProvider.setCookie(response, userAuthDto.getRefreshToken());
        return ResponseEntity.ok(LoginResponse.from(userAuthDto));
    }
}
