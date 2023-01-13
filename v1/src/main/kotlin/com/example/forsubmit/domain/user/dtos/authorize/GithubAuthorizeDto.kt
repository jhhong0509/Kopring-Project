package com.example.forsubmit.domain.user.dtos.authorize

import com.example.forsubmit.domain.user.properties.BaseOAuthProperty

class GithubAuthorizeDto(
    override val baseOAuthProperty: BaseOAuthProperty,
    override val url: String,
    override val endpoint: String
) : OAuthBaseAuthorizeDto