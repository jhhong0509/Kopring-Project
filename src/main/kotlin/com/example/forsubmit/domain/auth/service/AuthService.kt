package com.example.forsubmit.domain.auth.service

import com.example.forsubmit.domain.auth.entity.RefreshToken
import com.example.forsubmit.domain.auth.entity.RefreshTokenRepository
import com.example.forsubmit.domain.auth.exceptions.PasswordNotMatchException
import com.example.forsubmit.domain.auth.exceptions.RefreshTokenNotFoundException
import com.example.forsubmit.domain.auth.payload.request.AuthRequest
import com.example.forsubmit.domain.auth.payload.response.AccessTokenResponse
import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.user.`interface`.UserFacade
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userFacade: UserFacade,
    private val passwordEncoder: PasswordEncoder
) {
    fun signIn(request: AuthRequest) : TokenResponse {
        val user = userFacade.findUserByEmail(request.email)

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw PasswordNotMatchException.EXCEPTION
        }

        val tokenResponse = jwtTokenProvider.getToken(user.id)

        val refreshToken = RefreshToken(
            id = user.id,
            token = tokenResponse.refreshToken
        )

        refreshTokenRepository.save(refreshToken)

        return tokenResponse
    }

    fun tokenRefresh(refreshToken: String): AccessTokenResponse {
        val token = refreshTokenRepository.findByIdOrNull(refreshToken) ?: throw RefreshTokenNotFoundException.EXCEPTION
        val isRefreshToken = jwtTokenProvider.isRefreshToken(token.token)

        if (isRefreshToken == false) {
            throw RefreshTokenNotFoundException.EXCEPTION
        }

        return jwtTokenProvider.getAccessToken(token.id)
    }

}