package com.woowacourse.teatime.auth.config;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.util.http.SlackHttpClient;
import com.woowacourse.teatime.auth.support.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/v2/**");
    }

    @Bean
    public MethodsClient slackClient() {
        return new MethodsClientImpl(slackHttpClient());
    }

    @Bean
    public SlackHttpClient slackHttpClient() {
        return new SlackHttpClient();
    }
}
