package com.gn.gn_repair.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(deviceInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public DeviceInterceptor deviceInterceptor() {
        return new DeviceInterceptor();
    }
}