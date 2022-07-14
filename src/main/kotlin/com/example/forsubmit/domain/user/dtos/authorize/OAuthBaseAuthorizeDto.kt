package com.example.forsubmit.domain.user.dtos.authorize

import com.example.forsubmit.domain.user.properties.BaseOAuthProperty

sealed interface OAuthBaseAuthorizeDto {
    val baseOAuthProperty: BaseOAuthProperty
    val url: String
    val endpoint: String
}