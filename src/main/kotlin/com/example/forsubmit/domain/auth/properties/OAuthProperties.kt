package com.example.forsubmit.domain.auth.properties

import com.example.forsubmit.domain.auth.utils.OAuthPKCE
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration

@Configuration
@ConstructorBinding
@ConfigurationProperties(prefix = "oauth2")
class OAuthProperties {

    lateinit var google: Google
    lateinit var github: Github

    companion object {
        const val OAUTH_PROTOCOL = "https"
    }

    class Google(
        override val clientId: String,
        override val clientSecret: String,
        override val redirectUrl: String,
        override val scope: String
    ) : OAuthBaseProperty, OAuthPKCE {
        override val baseUrl = "https://accounts.google.com/o/oauth2/v2"
        override val authorizeUri = "/auth"
    }

    class Github(
        override val clientId: String,
        override val clientSecret: String,
        override val redirectUrl: String,
        override val scope: String
    ) : OAuthBaseProperty {
        override val baseUrl = "https://github.com/login/oauth"
        override val authorizeUri = "/authorize"
    }

}