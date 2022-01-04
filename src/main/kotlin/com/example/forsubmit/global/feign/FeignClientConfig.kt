package com.example.forsubmit.global.feign

import com.example.forsubmit.BASE_PACKAGE
import feign.Request
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@EnableFeignClients(basePackages = [BASE_PACKAGE])
@Configuration
class FeignClientConfig {

    @Bean
    fun options(): Request.Options {
        return Request.Options(5000, TimeUnit.MILLISECONDS, 30000, TimeUnit.MILLISECONDS, false)
    }
}