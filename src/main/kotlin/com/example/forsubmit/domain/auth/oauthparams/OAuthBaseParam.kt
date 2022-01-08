package com.example.forsubmit.domain.auth.oauthparams

import com.example.forsubmit.domain.auth.properties.OAuthBaseProperty
import org.springframework.util.MultiValueMap

interface OAuthBaseParam {
    val oAuthBaseProperty: OAuthBaseProperty

    fun getParams(): MultiValueMap<String, String>

}