package com.woowacourse.teatime.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedOrigins(
                        "http://localhost:8080",
                        "https://43.200.90.219:8080",
                        "http://43.200.90.219:8080"
                )
                .allowedMethods("*");
    }
}
