package com.example.forsubmit.domain.auth.oauthparams

import com.example.forsubmit.domain.auth.properties.OAuthBaseProperty
import com.example.forsubmit.domain.auth.utils.OAuthParamUtil
import org.springframework.util.MultiValueMap

class GoogleOAuthParams(
    override val codeChallenge: String,
    override val codeChallengeMethod: String,
    override val oAuthBaseProperty: OAuthBaseProperty
) : OAuthBaseParam, OAuthPKCE {

    override fun getParams(): MultiValueMap<String, String> {
        val params = OAuthParamUtil.buildBaseParam(oAuthBaseProperty)
        OAuthParamUtil.addPkceParam(
            params = params,
            codeChallenge = codeChallenge,
            codeChallengeMethod = codeChallengeMethod
        )

        return params
    }

}