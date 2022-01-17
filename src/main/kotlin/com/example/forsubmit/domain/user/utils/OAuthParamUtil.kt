package com.example.forsubmit.domain.user.utils

import com.example.forsubmit.domain.user.dtos.authorize.OAuthBaseAuthorizeDto
import com.example.forsubmit.domain.user.dtos.authorize.OAuthPKCEAuthorizeDto
import com.example.forsubmit.domain.user.dtos.token.OAuthBaseTokenDto
import com.example.forsubmit.domain.user.dtos.token.OAuthPKCETokenDto
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class OAuthParamUtil {
    companion object {
        fun buildAuthorizeParam(oAuthBaseAuthorizeDto: OAuthBaseAuthorizeDto): MultiValueMap<String, String> {
            val params = LinkedMultiValueMap<String, String>()

            if (oAuthBaseAuthorizeDto is OAuthPKCEAuthorizeDto) {
                params["code_challenge"] = oAuthBaseAuthorizeDto.codeChallenge
                params["code_challenge_method"] = oAuthBaseAuthorizeDto.codeChallengeMethod
            }

            params["response_type"] = "code"
            params["client_id"] = oAuthBaseAuthorizeDto.baseOAuthProperty.clientId
            params["scope"] = oAuthBaseAuthorizeDto.baseOAuthProperty.scope
            params["redirect_uri"] = oAuthBaseAuthorizeDto.baseOAuthProperty.redirectUri

            return params
        }

        fun buildBaseTokenParam(oAuthBaseTokenDto: OAuthBaseTokenDto): MultiValueMap<String, String> {
            val params = LinkedMultiValueMap<String, String>()

            if (oAuthBaseTokenDto is OAuthPKCETokenDto) {
                params["code_verifier"] = oAuthBaseTokenDto.codeVerifier
            }

            params["grant_type"] = "authorization_code"
            params["code"] = oAuthBaseTokenDto.code
            params["redirect_uri"] = oAuthBaseTokenDto.baseOAuthProperty.redirectUri
            params["client_id"] = oAuthBaseTokenDto.baseOAuthProperty.clientId
            params["client_secret"] = oAuthBaseTokenDto.baseOAuthProperty.clientSecret

            return params
        }

    }

}