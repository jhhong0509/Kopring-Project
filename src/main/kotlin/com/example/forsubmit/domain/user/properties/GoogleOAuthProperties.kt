package com.example.forsubmit.domain.user.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration

@Configuration
@ConstructorBinding
@ConfigurationProperties(prefix = "oauth2.google")
class GoogleOAuthProperties : BaseOAuthProperty {
    override lateinit var clientId: String
    override lateinit var clientSecret: String
    override lateinit var redirectUri: String
    override lateinit var scope: String

    companion object {
        const val AUTHORIZE_BASE_URL: String = "https://accounts.google.com/o/oauth2/v2"
        const val AUTHORIZE_ENDPOINT: String = "/auth"

        const val TOKEN_BASE_URL: String = "https://oauth2.googleapis.com"
        const val TOKEN_ENDPOINT: String = "/token"

        const val USER_INFO_BASE_URL: String = "https://www.googleapis.com/oauth2/v2"
        const val USER_INFO_ENDPOINT: String = "/userinfo"

        const val ACCEPT: String = "*/*"
    }
}