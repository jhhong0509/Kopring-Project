package com.example.forsubmit.domain.user.controller

import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.user.enums.OAuthType
import com.example.forsubmit.domain.user.payload.response.OAuthRedirectUriResponse
import com.example.forsubmit.domain.user.service.OAuthService
import com.example.forsubmit.global.payload.BaseResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/oauth")
class OAuthController(
    private val oAuthService: OAuthService
) {
    @GetMapping("/{type}/authentication-uri")
    fun oAuthAuthorizeUri(
        @PathVariable type: OAuthType,
        @RequestParam(required = false) codeChallenge: String?,
        @RequestParam(required = false) codeChallengeMethod: String?
    ): BaseResponse<OAuthRedirectUriResponse> {
        return oAuthService.getAuthorizeUri(type, codeChallenge, codeChallengeMethod)
    }

    @PostMapping("/{type}")
    fun oauthSignIn(
        @PathVariable type: OAuthType,
        @RequestParam(required = false) codeVerifier: String?,
        @RequestParam code: String
    ): BaseResponse<TokenResponse> {
        return oAuthService.oAuthSignInOrSignUp(type, codeVerifier, code)
    }
}