package com.woowacourse.teatime.auth.infrastructure;

import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

@RequiredArgsConstructor
@Component
public class PayloadExtractor {

    private final JwtTokenProvider jwtTokenProvider;

    public Claims extract(NativeWebRequest webRequest) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = AuthorizationExtractor.extract(request);
        return jwtTokenProvider.getPayload(token);
    }
}
