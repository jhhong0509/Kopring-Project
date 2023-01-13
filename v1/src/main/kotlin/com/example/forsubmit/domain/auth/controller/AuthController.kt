package com.example.forsubmit.domain.auth.controller

import com.example.forsubmit.domain.auth.payload.request.AuthRequest
import com.example.forsubmit.domain.auth.payload.response.AccessTokenResponse
import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.auth.service.AuthService
import com.example.forsubmit.global.payload.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun signIn(@RequestBody @Valid authRequest: AuthRequest): BaseResponse<TokenResponse> {
        return authService.signIn(authRequest)
    }

    @PutMapping
    fun refreshToken(@RequestHeader("Refresh-Token") refreshToken: String): BaseResponse<AccessTokenResponse> {
        return authService.tokenRefresh(refreshToken)
    }

}