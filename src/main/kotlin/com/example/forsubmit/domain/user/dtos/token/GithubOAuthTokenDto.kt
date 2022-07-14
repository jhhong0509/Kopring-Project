package com.example.forsubmit.domain.user.dtos.token

import com.example.forsubmit.domain.user.infrastructure.token.OAuthTokenClient
import com.example.forsubmit.domain.user.infrastructure.userinfo.OAuthUserInfoClient
import com.example.forsubmit.domain.user.properties.BaseOAuthProperty

class GithubOAuthTokenDto(
    override val baseOAuthProperty: BaseOAuthProperty,
    override val code: String,
    override val oAuthTokenClient: OAuthTokenClient,
    override val oAuthUserInfoClient: OAuthUserInfoClient,
    override val accept: String
) : OAuthBaseTokenDto