package com.example.forsubmit.global.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration
import kotlin.properties.Delegates

@Configuration
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.redis")
class RedisProperties {
    var port by Delegates.notNull<Int>()
    lateinit var host: String
}