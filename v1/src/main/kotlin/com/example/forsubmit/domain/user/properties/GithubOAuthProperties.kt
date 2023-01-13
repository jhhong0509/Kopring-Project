package com.example.forsubmit.domain.user.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "oauth2.github")
class GithubOAuthProperties(
    override val clientId: String,
    override val clientSecret: String,
    override val redirectUri: String,
    override val scope: String
) : BaseOAuthProperty {
    companion object {
        const val AUTHORIZE_BASE_URL: String = "https://github.com/login/oauth"
        const val AUTHORIZE_ENDPOINT: String = "/authorize"

        const val TOKEN_BASE_URL: String = "https://github.com/login/oauth"
        const val TOKEN_ENDPOINT: String = "/access_token"

        const val USER_INFO_BASE_URL: String = "https://api.github.com"
        const val USER_INFO_ENDPOINT: String = "/user"

        const val ACCEPT: String = "application/vnd.github.v3+json"
    }
}