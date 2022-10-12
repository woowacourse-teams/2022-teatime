package com.woowacourse.teatime.auth.infrastructure;

import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpCookie.SameSite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class ResponseCookieTokenProvider {

    private final Long expireLength;

    public ResponseCookieTokenProvider(@Value("${refresh-token.expire-length}") String expireLength) {
        this.expireLength = Long.parseLong(expireLength);
    }

    public void setCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(expireLength)
                .path("/")
                .secure(true)
                .sameSite(SameSite.NONE.name())
                .httpOnly(true)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
