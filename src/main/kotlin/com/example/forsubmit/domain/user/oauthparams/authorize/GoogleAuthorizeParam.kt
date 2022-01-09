package com.example.forsubmit.domain.auth.oauthparams.authorize

import com.example.forsubmit.domain.auth.properties.OAuthBaseProperty
import com.example.forsubmit.domain.auth.utils.OAuthParamUtil
import org.springframework.util.MultiValueMap

class GoogleAuthorizeParam(
    override val codeChallenge: String,
    override val codeChallengeMethod: String,
    override val oAuthBaseProperty: OAuthBaseProperty
) : OAuthBaseAuthorizeParam, OAuthPKCEAuthorizeParam