package com.example.forsubmit.domain.auth.exceptions

import com.example.forsubmit.global.exception.property.ExceptionProperty

enum class AuthErrorCode(
    override val status: Int,
    override val errorMessage: String,
    override val koreanMessage: String
) : ExceptionProperty {
    PASSWORD_NOT_MATCH(404, "Password Not Match", "비밀번호가 일치하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(404, "RefreshToken Not Found", "Refresh Token 을 찾을 수 없습니다.")
}