package com.example.forsubmit.domain.user.infrastructure.userinfo

import com.example.forsubmit.domain.user.infrastructure.userinfo.dto.BaseUserInfoResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

sealed interface OAuthUserInfoClient {
    @GetMapping
    fun getUserInfo(token: String): BaseUserInfoResponse
}