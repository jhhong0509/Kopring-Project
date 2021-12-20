package com.example.forsubmit.global.security.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration
import kotlin.properties.Delegates

@Configuration
@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
class JwtProperties {
    lateinit var secretKey: String
    var accessTokenExp by Delegates.notNull<Long>()
    var refreshTokenExp by Delegates.notNull<Long>()

    companion object {
        const val TOKEN_PREFIX = "Bearer "
        const val TOKEN_HEADER_NAME = "Authorization"
        const val ACCESS_VALUE = "access"
        const val REFRESH_VALUE = "refresh"
    }
}