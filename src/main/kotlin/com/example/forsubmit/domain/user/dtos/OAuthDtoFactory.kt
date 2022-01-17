package com.example.forsubmit.domain.user.dtos

import com.example.forsubmit.domain.user.enums.OAuthType
import com.example.forsubmit.domain.user.infrastructure.token.GithubTokenClient
import com.example.forsubmit.domain.user.infrastructure.token.GoogleTokenClient
import com.example.forsubmit.domain.user.infrastructure.userinfo.GithubUserInfoClient
import com.example.forsubmit.domain.user.infrastructure.userinfo.GoogleUserInfoClient
import com.example.forsubmit.domain.user.dtos.authorize.GithubAuthorizeDto
import com.example.forsubmit.domain.user.dtos.authorize.GoogleAuthorizeDto
import com.example.forsubmit.domain.user.dtos.authorize.OAuthBaseAuthorizeDto
import com.example.forsubmit.domain.user.dtos.token.GithubOAuthTokenDto
import com.example.forsubmit.domain.user.dtos.token.GoogleOAuthTokenDto
import com.example.forsubmit.domain.user.dtos.token.OAuthBaseTokenDto
import com.example.forsubmit.domain.user.properties.GithubOAuthProperties
import com.example.forsubmit.domain.user.properties.GoogleOAuthProperties
import org.springframework.stereotype.Component

@Component
class OAuthDtoFactory(
    private val googleOAuthProperties: GoogleOAuthProperties,
    private val githubOAuthProperties: GithubOAuthProperties,
    private val googleTokenClient: GoogleTokenClient,
    private val githubTokenClient: GithubTokenClient,
    private val googleUserInfoClient: GoogleUserInfoClient,
    private val githubUserInfoClient: GithubUserInfoClient
) {

    fun getTokenDto(type: OAuthType, code: String, codeVerifier: String?): OAuthBaseTokenDto {
        return when (type) {
            OAuthType.GITHUB -> GithubOAuthTokenDto(
                baseOAuthProperty = githubOAuthProperties,
                code = code,
                oAuthTokenClient = githubTokenClient,
                oAuthUserInfoClient = githubUserInfoClient,
                accept = GoogleOAuthProperties.ACCEPT
            )
            OAuthType.GOOGLE -> GoogleOAuthTokenDto(
                baseOAuthProperty = googleOAuthProperties,
                code = code,
                codeVerifier = codeVerifier!!,
                oAuthTokenClient = googleTokenClient,
                oAuthUserInfoClient = googleUserInfoClient,
                accept = GoogleOAuthProperties.ACCEPT
            )
        }
    }

    fun getAuthorizeDto(
        type: OAuthType,
        codeChallenge: String?,
        codeChallengeMethod: String?
    ): OAuthBaseAuthorizeDto {

        return when (type) {

            OAuthType.GITHUB -> GithubAuthorizeDto(
                baseOAuthProperty = githubOAuthProperties,
                url = GithubOAuthProperties.AUTHORIZE_BASE_URL,
                endpoint = GithubOAuthProperties.AUTHORIZE_ENDPOINT
            )

            OAuthType.GOOGLE -> GoogleAuthorizeDto(
                codeChallenge = codeChallenge!!,
                codeChallengeMethod = codeChallengeMethod!!,
                baseOAuthProperty = googleOAuthProperties,
                url = GoogleOAuthProperties.AUTHORIZE_BASE_URL,
                endpoint = GoogleOAuthProperties.AUTHORIZE_ENDPOINT
            )

        }

    }

}