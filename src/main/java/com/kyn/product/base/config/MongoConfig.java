package com.kyn.product.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;

import reactor.core.publisher.Mono;

@Configuration
public class MongoConfig {

    /**
     * auditing
     */
    @Bean
    public ReactiveAuditorAware<String> reactiveAuditorAware() {
        return () -> Mono.just("system");  // 현재는 고정된 "system" 값 사용
    }
} 