package com.example.forsubmit.domain.user.dtos.authorize

import com.example.forsubmit.domain.user.properties.BaseOAuthProperty

class GoogleAuthorizeDto(
    override val codeChallenge: String,
    override val codeChallengeMethod: String,
    override val baseOAuthProperty: BaseOAuthProperty,
    override val url: String,
    override val endpoint: String,
) : OAuthBaseAuthorizeDto, OAuthPKCEAuthorizeDto