package com.example.forsubmit.domain.user.infrastructure.token

import com.example.forsubmit.domain.user.infrastructure.token.dto.BaseTokenResponse
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.PostMapping

sealed interface OAuthTokenClient {
    @PostMapping
    fun getToken(param: MultiValueMap<String, String>): BaseTokenResponse
}