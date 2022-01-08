package com.example.forsubmit.domain.auth.service

import com.example.forsubmit.domain.auth.exceptions.InvalidOauthTypeException
import com.example.forsubmit.domain.auth.oauthparams.GithubOAuthParams
import com.example.forsubmit.domain.auth.oauthparams.GoogleOAuthParams
import com.example.forsubmit.domain.auth.payload.response.OAuthRedirectUriResponse
import com.example.forsubmit.domain.auth.properties.GithubOAuthProperties
import com.example.forsubmit.domain.auth.properties.GoogleOAuthProperties
import com.example.forsubmit.global.payload.BaseResponse
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder


@Service
class OAuthService(
    private val githubOAuthProperties: GithubOAuthProperties,
    private val googleOAuthProperties: GoogleOAuthProperties
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
            "github" -> {
                val params = GithubOAuthParams(githubOAuthProperties)
                getAuthUri(params.getParams(), GithubOAuthProperties.BASE_URL, GithubOAuthProperties.AUTHORIZE_URL)
            }
            "google" -> {
                val params = GoogleOAuthParams(codeChallenge!!, codeChallengeMethod!!, googleOAuthProperties)
                getAuthUri(params.getParams(), GoogleOAuthProperties.BASE_URL, GoogleOAuthProperties.AUTHORIZE_URL)
            }
            else -> throw InvalidOauthTypeException.EXCEPTION
        }

        return BaseResponse(
            status = 200,
            message = GET_AUTH_URL_MESSAGE,
            koreanMessage = GET_AUTH_URL_MESSAGE_KOR,
            content = OAuthRedirectUriResponse(authUri)
        )
    }

    private fun getAuthUri(params: MultiValueMap<String, String>, baseUrl: String, authorizeUri: String): String {
        return UriComponentsBuilder
            .fromHttpUrl(baseUrl)
            .path(authorizeUri)
            .queryParams(params)
            .toUriString()
    }

}