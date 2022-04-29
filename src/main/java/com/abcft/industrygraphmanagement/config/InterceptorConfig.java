package com.abcft.industrygraphmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Created by YangMeng on 2021/5/11 15:24
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizeInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login/index/**");
    }

    @Bean
    public AuthorizeInterceptor authorizeInterceptor() {
        return new AuthorizeInterceptor();
    }
}
