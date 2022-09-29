package com.woowacourse.teatime.auth.support;

import com.woowacourse.teatime.auth.exception.UnAuthorizedTokenException;
import com.woowacourse.teatime.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.teatime.util.AuthorizationExtractor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }

        String token = AuthorizationExtractor.extract(request);
        if (jwtTokenProvider.validateToken(token)) {
            return true;
        }
        throw new UnAuthorizedTokenException();
    }
}
