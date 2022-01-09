package com.example.forsubmit.domain.auth.oauthparams.token

import com.example.forsubmit.domain.auth.properties.OAuthBaseProperty

class GoogleOAuthTokenParam(
    override val oAuthBaseProperty: OAuthBaseProperty,
    override val code: String,
    override val codeVerifier: String
) : OAuthBaseTokenParam, OAuthPKCETokenParam