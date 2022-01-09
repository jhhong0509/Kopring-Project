package com.example.forsubmit.domain.auth.oauthparams.token

import com.example.forsubmit.domain.auth.properties.OAuthBaseProperty

class GithubOAuthTokenParam(
    override val code: String,
    override val oAuthBaseProperty: OAuthBaseProperty
) : OAuthBaseTokenParam