package com.woowacourse.teatime.teatime.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig implements WebMvcConfigurer {

    @Value("${slack.bot.url}")
    private String url;

    @Bean
    public WebClient botClient() {
        return WebClient.create(url);
    }
}
