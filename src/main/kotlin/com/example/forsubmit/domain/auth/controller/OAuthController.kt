package com.example.forsubmit.domain.auth.controller

import com.example.forsubmit.domain.auth.payload.response.OAuthRedirectUrlResponse
import com.example.forsubmit.domain.auth.service.OAuthService
import com.example.forsubmit.global.payload.BaseResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/oauth")
class OAuthController(
    private val oAuthService: OAuthService
) {
    @GetMapping("/auth-endpoint/{type}")
    fun oauthLink(@PathVariable type: String): BaseResponse<OAuthRedirectUrlResponse> {
        return oAuthService.getAuthenticationUri(type)
    }
}