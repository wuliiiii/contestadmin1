package org.algotithmcontestdatacollect.displaybackend.Configure;

import org.algotithmcontestdatacollect.displaybackend.Interceptor.JWTInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private JWTInterceptor JWTinterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(JWTinterceptor).addPathPatterns("/api/user/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
