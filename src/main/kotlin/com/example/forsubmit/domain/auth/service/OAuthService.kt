package com.example.forsubmit.domain.auth.service

import com.example.forsubmit.domain.auth.exceptions.InvalidOauthTypeException
import com.example.forsubmit.domain.auth.payload.response.OAuthRedirectUrlResponse
import com.example.forsubmit.domain.auth.properties.OAuthBaseProperty
import com.example.forsubmit.domain.auth.properties.OAuthPKCE
import com.example.forsubmit.domain.auth.properties.OAuthProperties
import com.example.forsubmit.global.payload.BaseResponse
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder


@Service
class OAuthService(
    private val oAuthProperties: OAuthProperties
) {

    companion object {
        const val GET_AUTH_URL_MESSAGE = "Get Authentication Url Success"
        const val GET_AUTH_URL_MESSAGE_KOR = "인증 URL을 성공적으로 반환했습니다."
    }

    fun getAuthenticationUri(type: String): BaseResponse<OAuthRedirectUrlResponse> {

        val redirectUri = when (type) {
            "google" -> getAuthUri(oAuthProperties.google)
            "github" -> getAuthUri(oAuthProperties.github)
            else -> throw InvalidOauthTypeException.EXCEPTION
        }

        return BaseResponse(
            status = 200,
            message = GET_AUTH_URL_MESSAGE,
            koreanMessage = GET_AUTH_URL_MESSAGE_KOR,
            content = OAuthRedirectUrlResponse(redirectUri)
        )
    }

    private fun getAuthUri(baseProperty: OAuthBaseProperty): String {
        val params = buildParams(baseProperty)

        return UriComponentsBuilder
            .fromHttpUrl(baseProperty.baseUrl)
            .path(baseProperty.authorizeUri)
            .queryParams(params)
            .toUriString()
    }

    private fun buildParams(properties: OAuthBaseProperty): MultiValueMap<String, String> {
        val params = when (properties) {
            is OAuthPKCE -> buildChallengeParam(properties)
            else -> LinkedMultiValueMap()
        }

        params["client_id"] = properties.clientId
        params["redirect_uri"] = properties.redirectUrl
        params["scope"] = properties.scope
        params["response_type"] = "code"

        return params
    }

    private fun buildChallengeParam(oAuthPKCE: OAuthPKCE): MultiValueMap<String, String> {
        val params = LinkedMultiValueMap<String, String>()
        val codeVerifier = oAuthPKCE.generateCodeVerifier()
        val codeChallenge = oAuthPKCE.generateCodeChallenge(codeVerifier)

        params["code_challenge_method"] = "S256"
        params["code_challenge"] = codeChallenge

        return params
    }

}