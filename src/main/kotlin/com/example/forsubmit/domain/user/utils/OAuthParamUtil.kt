package com.example.forsubmit.domain.auth.utils

import com.example.forsubmit.domain.auth.oauthparams.authorize.OAuthBaseAuthorizeParam
import com.example.forsubmit.domain.auth.oauthparams.authorize.OAuthPKCEAuthorizeParam
import com.example.forsubmit.domain.auth.oauthparams.token.OAuthBaseTokenParam
import com.example.forsubmit.domain.auth.oauthparams.token.OAuthPKCETokenParam
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class OAuthParamUtil {
    companion object {
        fun buildAuthorizeParam(oAuthBaseAuthorizeParam: OAuthBaseAuthorizeParam): MultiValueMap<String, String> {
            val params = LinkedMultiValueMap<String, String>()

            if (oAuthBaseAuthorizeParam is OAuthPKCEAuthorizeParam) {
                params["code_challenge"] = oAuthBaseAuthorizeParam.codeChallenge
                params["code_challenge_method"] = oAuthBaseAuthorizeParam.codeChallengeMethod
            }

            params["response_type"] = "code"
            params["client_id"] = oAuthBaseAuthorizeParam.oAuthBaseProperty.clientId
            params["scope"] = oAuthBaseAuthorizeParam.oAuthBaseProperty.scope
            params["redirect_uri"] = oAuthBaseAuthorizeParam.oAuthBaseProperty.redirectUri

            return params
        }

        fun buildBaseTokenParam(oAuthBaseTokenParam: OAuthBaseTokenParam): MultiValueMap<String, String> {
            val params = LinkedMultiValueMap<String, String>()

            if (oAuthBaseTokenParam is OAuthPKCETokenParam) {
                params["code_verifier"] = oAuthBaseTokenParam.codeVerifier
            }

            params["grant_type"] = "authorization_code"
            params["code"] = oAuthBaseTokenParam.code
            params["redirect_uri"] = oAuthBaseTokenParam.oAuthBaseProperty.redirectUri
            params["client_id"] = oAuthBaseTokenParam.oAuthBaseProperty.clientId
            params["client_secret"] = oAuthBaseTokenParam.oAuthBaseProperty.clientSecret

            return params
        }

    }

}