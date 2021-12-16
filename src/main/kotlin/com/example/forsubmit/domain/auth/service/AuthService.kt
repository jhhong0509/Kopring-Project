package com.example.forsubmit.domain.auth.service

import com.example.forsubmit.domain.auth.entity.RefreshToken
import com.example.forsubmit.domain.auth.entity.RefreshTokenRepository
import com.example.forsubmit.domain.auth.exceptions.PasswordNotMatchException
import com.example.forsubmit.domain.auth.exceptions.RefreshTokenNotFoundException
import com.example.forsubmit.domain.auth.payload.request.AuthRequest
import com.example.forsubmit.domain.auth.payload.response.AccessTokenResponse
import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.user.facade.UserFacade
import com.example.forsubmit.global.payload.BaseResponse
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

    companion object {
        private const val SIGN_IN_MESSAGE = "Success to SignIn"
        private const val SIGN_IN_MESSAGE_KOR = "로그인에 성공했습니다"
        private const val TOKEN_REFRESH_MESSAGE = "Success to Refresh Token"
        private const val TOKEN_REFRESH_MESSAGE_KOR = "토큰 재발급에 성공했습니다."
    }

    fun signIn(request: AuthRequest): BaseResponse<TokenResponse> {
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

        return BaseResponse(
            status = 201,
            message = SIGN_IN_MESSAGE,
            koreanMessage = SIGN_IN_MESSAGE_KOR,
            content = tokenResponse
        )
    }

    fun tokenRefresh(refreshToken: String): BaseResponse<AccessTokenResponse> {
        val token = refreshTokenRepository.findByToken(refreshToken) ?: throw RefreshTokenNotFoundException.EXCEPTION
        token.ttl = refreshExp
        refreshTokenRepository.save(token)
        return BaseResponse(
            status = 200,
            message = TOKEN_REFRESH_MESSAGE,
            koreanMessage = TOKEN_REFRESH_MESSAGE_KOR,
            content = jwtTokenProvider.getAccessToken(token.email)
        )
    }

}