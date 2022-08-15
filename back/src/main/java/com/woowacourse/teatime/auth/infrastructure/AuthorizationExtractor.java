package com.woowacourse.teatime.auth.infrastructure;

import com.google.common.net.HttpHeaders;
import com.woowacourse.teatime.auth.exception.InvalidTokenFormatException;
import com.woowacourse.teatime.auth.exception.NoAuthorizationHeaderException;
import javax.servlet.http.HttpServletRequest;

public class AuthorizationExtractor {

    private static final String BEARER_TYPE = "Bearer";

    public static String extract(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader == null) {
            throw new NoAuthorizationHeaderException();
        }

        validateAuthorizationFormat(authorizationHeader);

        return authorizationHeader.substring(BEARER_TYPE.length()).trim();
    }

    private static void validateAuthorizationFormat(String authorizationHeader) {
        if (!authorizationHeader.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            throw new InvalidTokenFormatException();
        }
    }
}
