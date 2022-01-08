package com.example.forsubmit.domain.auth.utils

import com.example.forsubmit.domain.auth.properties.OAuthBaseProperty
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class OAuthParamUtil {
    companion object {
        fun buildBaseParam(oAuthBaseProperty: OAuthBaseProperty): MultiValueMap<String, String> {
            val params = LinkedMultiValueMap<String, String>()

            params["response_type"] = "code"
            params["client_id"] = oAuthBaseProperty.clientId
            params["scope"] = oAuthBaseProperty.scope
            params["redirect_uri"] = oAuthBaseProperty.redirectUri

            return params
        }

        fun addPkceParam(
            params: MultiValueMap<String, String>,
            codeChallenge: String,
            codeChallengeMethod: String
        ): MultiValueMap<String, String> {

            params["code_challenge"] = codeChallenge
            params["code_challenge_method"] = codeChallengeMethod

            return params
        }
    }
}