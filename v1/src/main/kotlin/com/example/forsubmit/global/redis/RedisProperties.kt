package com.example.forsubmit.global.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.redis")
class RedisProperties(
    var port: Int,
    val host: String
)