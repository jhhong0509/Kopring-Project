package com.example.forsubmit.domain.user.service

import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.entity.UserRepository
import com.example.forsubmit.domain.user.exceptions.EmailAlreadyExistsException
import com.example.forsubmit.domain.user.payload.request.SignUpRequest
import com.example.forsubmit.global.payload.BaseResponse
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {
    companion object {
        private const val SIGN_UP_MESSAGE = "Sign Up Success"
        private const val SIGN_UP_MESSAGE_KOR = "회원가입에 성공했습니다."
    }

    fun saveUser(request: SignUpRequest): BaseResponse<TokenResponse> {
        userRepository.findByEmail(request.email)?.let { throw EmailAlreadyExistsException.EXCEPTION }

        val user = User(
            name = request.name,
            email = request.email,
            password = request.password
        )

        userRepository.save(user)

        val tokenResponse = jwtTokenProvider.getToken(user.email)

        return BaseResponse(
            status = 201,
            message = SIGN_UP_MESSAGE,
            koreanMessage = SIGN_UP_MESSAGE_KOR,
            content = tokenResponse
        )
    }

}