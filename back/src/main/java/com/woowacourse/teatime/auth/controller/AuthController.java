package com.woowacourse.teatime.auth.controller;

import com.woowacourse.teatime.auth.controller.dto.GenerateTokenDto;
import com.woowacourse.teatime.auth.controller.dto.LoginRequest;
import com.woowacourse.teatime.auth.controller.dto.LoginResponse;
import com.woowacourse.teatime.auth.controller.dto.RefreshAccessTokenResponse;
import com.woowacourse.teatime.auth.controller.dto.UserAuthDto;
import com.woowacourse.teatime.auth.infrastructure.ResponseCookieTokenProvider;
import com.woowacourse.teatime.auth.service.AuthService;
import com.woowacourse.teatime.auth.service.LoginService;
import com.woowacourse.teatime.auth.service.UserAuthService;
import com.woowacourse.teatime.auth.support.UserAuthenticationPrincipal;
import com.woowacourse.teatime.auth.support.dto.UserRoleDto;
import com.woowacourse.teatime.util.AuthorizationExtractor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final UserAuthService userAuthService;
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

    @GetMapping("/refresh-token")
    public ResponseEntity<RefreshAccessTokenResponse> generateToken(
            @CookieValue(value = "refreshToken", required = false) Cookie cookie,
            HttpServletRequest request,
            HttpServletResponse response) {
        String token = AuthorizationExtractor.extract(request);
        GenerateTokenDto generateTokenDto = userAuthService.generateToken(cookie, token);
        cookieTokenProvider.setCookie(response, generateTokenDto.getRefreshToken());
        return ResponseEntity.ok(new RefreshAccessTokenResponse(generateTokenDto.getAccessToken()));
    }
}
