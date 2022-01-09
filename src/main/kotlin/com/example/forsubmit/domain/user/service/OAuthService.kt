package com.example.forsubmit.domain.auth.service

import com.example.forsubmit.domain.auth.exceptions.InvalidOauthTypeException
import com.example.forsubmit.domain.auth.infrastructure.GoogleOAuthClient
import com.example.forsubmit.domain.auth.infrastructure.dto.GoogleTokenResponse
import com.example.forsubmit.domain.auth.oauthparams.authorize.GithubAuthorizeParam
import com.example.forsubmit.domain.auth.oauthparams.authorize.GoogleAuthorizeParam
import com.example.forsubmit.domain.auth.oauthparams.token.GithubOAuthTokenParam
import com.example.forsubmit.domain.auth.oauthparams.token.GoogleOAuthTokenParam
import com.example.forsubmit.domain.auth.payload.response.OAuthRedirectUriResponse
import com.example.forsubmit.domain.auth.properties.GithubOAuthProperties
import com.example.forsubmit.domain.auth.properties.GoogleOAuthProperties
import com.example.forsubmit.domain.auth.utils.OAuthParamUtil
import com.example.forsubmit.domain.user.entity.UserRepository
import com.example.forsubmit.global.payload.BaseResponse
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder


@Service
class OAuthService(
    private val githubOAuthProperties: GithubOAuthProperties,
    private val googleOAuthProperties: GoogleOAuthProperties,
    private val googleOAuthClient: GoogleOAuthClient,
    private val userRepository: UserRepository,
) {

    companion object {
        const val GET_AUTH_URL_MESSAGE = "Get Authentication Url Success"
        const val GET_AUTH_URL_MESSAGE_KOR = "인증 URL을 성공적으로 반환했습니다."
    }

    fun getAuthorizeUri(
        type: String,
        codeChallenge: String?,
        codeChallengeMethod: String?
    ): BaseResponse<OAuthRedirectUriResponse> {

        val authUri = when (type) {
            "github" -> getAuthUri(
                OAuthParamUtil.buildAuthorizeParam(GithubAuthorizeParam(githubOAuthProperties)),
                GithubOAuthProperties.BASE_URL,
                GithubOAuthProperties.AUTHORIZE_URL
            )
            "google" -> getAuthUri(
                OAuthParamUtil.buildAuthorizeParam(
                    GoogleAuthorizeParam(
                        codeChallenge!!,
                        codeChallengeMethod!!,
                        googleOAuthProperties
                    )
                ),
                GoogleOAuthProperties.BASE_URL,
                GoogleOAuthProperties.AUTHORIZE_URL
            )
            else -> throw InvalidOauthTypeException.EXCEPTION
        }

        return BaseResponse(
            status = 200,
            message = GET_AUTH_URL_MESSAGE,
            koreanMessage = GET_AUTH_URL_MESSAGE_KOR,
            content = OAuthRedirectUriResponse(authUri)
        )
    }

    fun oAuthSignIn(type: String, codeVerifier: String?, code: String): GoogleTokenResponse {

        val params = when (type) {
            "google" -> OAuthParamUtil.buildBaseTokenParam(
                GoogleOAuthTokenParam(
                    oAuthBaseProperty = googleOAuthProperties,
                    code = code,
                    codeVerifier = codeVerifier!!
                )
            )
            "github" -> OAuthParamUtil.buildBaseTokenParam(
                GithubOAuthTokenParam(
                    oAuthBaseProperty = googleOAuthProperties,
                    code = code
                )
            )
            else -> throw InvalidOauthTypeException.EXCEPTION
        }

        return googleOAuthClient.getGoogleToken(params)
    }

    private fun getAuthUri(params: MultiValueMap<String, String>, baseUrl: String, authorizeUri: String): String {
        return UriComponentsBuilder
            .fromHttpUrl(baseUrl)
            .path(authorizeUri)
            .queryParams(params)
            .toUriString()
    }

}