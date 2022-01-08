package com.example.forsubmit.domain.auth.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration

@Configuration
@ConstructorBinding
@ConfigurationProperties(prefix = "oauth2.google")
class GoogleOAuthProperties : OAuthBaseProperty {
    override lateinit var clientId: String
    override lateinit var clientSecret: String
    override lateinit var redirectUri: String
    override lateinit var scope: String

    companion object {
        const val BASE_URL = "https://accounts.google.com/o/oauth2/v2"
        const val AUTHORIZE_URL = "/auth"
    }
}