package com.example.forsubmit.global.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.redis")
class RedisProperty(
    val port: Int,
    val host: String
)