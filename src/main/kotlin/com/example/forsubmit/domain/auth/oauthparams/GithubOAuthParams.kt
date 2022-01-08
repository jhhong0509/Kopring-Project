package com.example.forsubmit.domain.auth.oauthparams

import com.example.forsubmit.domain.auth.properties.OAuthBaseProperty
import com.example.forsubmit.domain.auth.utils.OAuthParamUtil
import org.springframework.util.MultiValueMap

class GithubOAuthParams(
    override val oAuthBaseProperty: OAuthBaseProperty
) : OAuthBaseParam {

    override fun getParams(): MultiValueMap<String, String> {
        return OAuthParamUtil.buildBaseParam(oAuthBaseProperty)
    }

}