package com.example.forsubmit.global.feign

import com.example.forsubmit.BASE_PACKAGE
import feign.RequestInterceptor
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableFeignClients(basePackages = [BASE_PACKAGE])
@Configuration
class FeignClientConfig {
    @Bean
    fun requestInterceptor(): RequestInterceptor {
        return RequestInterceptor { requestTemplate ->
            requestTemplate.header("Content-Type", "application/json")
            requestTemplate.header("Accept", "application/json")
        }
    }
}