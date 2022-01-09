package com.example.forsubmit.domain.auth.oauthparams.token

import com.example.forsubmit.domain.auth.properties.OAuthBaseProperty

interface OAuthBaseTokenParam {

    val oAuthBaseProperty: OAuthBaseProperty
    val code: String

}