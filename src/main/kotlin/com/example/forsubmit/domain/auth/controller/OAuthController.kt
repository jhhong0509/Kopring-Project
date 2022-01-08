package com.example.forsubmit.domain.auth.controller

import com.example.forsubmit.domain.auth.payload.response.OAuthRedirectUriResponse
import com.example.forsubmit.domain.auth.service.OAuthService
import com.example.forsubmit.global.payload.BaseResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/oauth")
class OAuthController(
    private val oAuthService: OAuthService
) {
    @GetMapping("/auth-endpoint/{type}")
    fun oauthAuthorizeUrl(
        @PathVariable type: String,
        @RequestParam(required = false) codeChallenge: String?,
        @RequestParam(required = false) codeChallengeMethod: String?
    ): BaseResponse<OAuthRedirectUriResponse> {
        return oAuthService.getAuthorizeUri(type, codeChallenge, codeChallengeMethod)
    }
}