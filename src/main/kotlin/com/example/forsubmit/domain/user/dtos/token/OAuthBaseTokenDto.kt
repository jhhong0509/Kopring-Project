package com.example.forsubmit.domain.user.dtos.token

import com.example.forsubmit.domain.user.infrastructure.token.OAuthTokenClient
import com.example.forsubmit.domain.user.infrastructure.userinfo.OAuthUserInfoClient
import com.example.forsubmit.domain.user.properties.BaseOAuthProperty

sealed interface OAuthBaseTokenDto {

    val oAuthTokenClient: OAuthTokenClient
    val baseOAuthProperty: BaseOAuthProperty
    val code: String
    val oAuthUserInfoClient: OAuthUserInfoClient
    val accept: String

}