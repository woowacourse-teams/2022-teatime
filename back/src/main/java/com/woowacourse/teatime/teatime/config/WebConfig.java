package com.woowacourse.teatime.teatime.config;

import com.google.common.net.HttpHeaders;
import com.woowacourse.teatime.auth.support.CoachArgumentResolver;
import com.woowacourse.teatime.auth.support.CrewArgumentResolver;
import com.woowacourse.teatime.auth.support.UserArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    private final CoachArgumentResolver coachArgumentResolver;
    private final CrewArgumentResolver crewArgumentResolver;
    private final UserArgumentResolver userArgumentResolver;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(
                        "http://localhost:8080",
                        "https://dev.teatime.pe.kr",
                        "https://teatime.pe.kr",
                        "http://teatime.pe.kr"
                )
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .exposedHeaders(HttpHeaders.LOCATION);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.addAll(List.of(
                coachArgumentResolver,
                crewArgumentResolver,
                userArgumentResolver));
    }
}
