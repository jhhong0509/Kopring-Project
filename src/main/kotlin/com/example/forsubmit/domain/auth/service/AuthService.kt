package com.example.forsubmit.domain.auth.service

import com.example.forsubmit.domain.auth.entity.RefreshToken
import com.example.forsubmit.domain.auth.entity.RefreshTokenRepository
import com.example.forsubmit.domain.auth.exceptions.PasswordNotMatchException
import com.example.forsubmit.domain.auth.exceptions.RefreshTokenNotFoundException
import com.example.forsubmit.domain.auth.payload.request.AuthRequest
import com.example.forsubmit.domain.auth.payload.response.AccessTokenResponse
import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.user.facade.UserFacade
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import com.example.forsubmit.global.security.property.JwtProperties
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userFacade: UserFacade,
    private val passwordEncoder: PasswordEncoder,
    jwtProperties: JwtProperties
) {
    private val refreshExp: Long

    init {
        refreshExp = jwtProperties.refreshTokenExp
    }

    fun signIn(request: AuthRequest): TokenResponse {
        val user = userFacade.findUserByEmail(request.email)

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw PasswordNotMatchException.EXCEPTION
        }

        val tokenResponse = jwtTokenProvider.getToken(user.email)

        val refreshToken = RefreshToken(
            email = user.email,
            token = tokenResponse.refreshToken,
            ttl = refreshExp
        )

        refreshTokenRepository.save(refreshToken)

        return tokenResponse
    }

    fun tokenRefresh(refreshToken: String): AccessTokenResponse {
        val token = refreshTokenRepository.findByToken(refreshToken) ?: throw RefreshTokenNotFoundException.EXCEPTION
        token.ttl = refreshExp
        refreshTokenRepository.save(token)
        return jwtTokenProvider.getAccessToken(token.email)
    }

}