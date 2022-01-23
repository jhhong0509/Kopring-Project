package com.example.forsubmit.domain.user.service

import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.facade.UserFacade
import com.example.forsubmit.domain.user.payload.request.SignUpRequest
import com.example.forsubmit.global.payload.BaseResponse
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userFacade: UserFacade,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder
) {
    companion object {
        private const val SIGN_UP_MESSAGE = "Sign Up Success"
        private const val SIGN_UP_MESSAGE_KOR = "회원가입에 성공했습니다."
    }

    fun saveUser(request: SignUpRequest): BaseResponse<TokenResponse> {
        val user = User(
            name = request.name,
            accountId = request.accountId,
            password = passwordEncoder.encode(request.password)
        )

        userFacade.saveUser(user)

        val tokenResponse = jwtTokenProvider.getToken(user.accountId)

        return BaseResponse(
            status = 201,
            message = SIGN_UP_MESSAGE,
            koreanMessage = SIGN_UP_MESSAGE_KOR,
            content = tokenResponse
        )
    }

}