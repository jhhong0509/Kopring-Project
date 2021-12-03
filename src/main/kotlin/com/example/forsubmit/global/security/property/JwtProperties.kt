package com.example.forsubmit.global.security.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
class JwtProperties(
    val secretKey: String,
    val accessTokenExp: Long,
    val refreshTokenExp: Long
) {
    companion object {
        const val TOKEN_PREFIX = "Bearer "
        const val TOKEN_HEADER_NAME = "Authorization"
        const val ACCESS_VALUE = "access"
        const val REFRESH_VALUE = "refresh"
    }
}