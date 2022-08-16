package com.woowacourse.teatime.auth.support;

import com.woowacourse.teatime.auth.exception.UnAuthorizedTokenException;
import com.woowacourse.teatime.auth.infrastructure.PayloadExtractor;
import com.woowacourse.teatime.teatime.domain.Role;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class CoachArgumentResolver implements HandlerMethodArgumentResolver {

    private final PayloadExtractor payloadExtractor;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CoachAuthenticationPrincipal.class);
    }

    @Override
    public Long resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Claims payload = payloadExtractor.extract(webRequest);
        Role role = payload.get("role", Role.class);
        if (!role.isCoach()) {
            throw new UnAuthorizedTokenException();
        }

        Long id = payload.get("id", Long.class);
        if (id == null) {
            throw new UnAuthorizedTokenException();
        }
        return id;
    }
}