package com.example.forsubmit.domain.user.exceptions

import com.example.forsubmit.global.exception.property.ExceptionProperty

enum class UserErrorCode(
    override val status: Int,
    override val errorMessage: String,
    override val koreanMessage: String
): ExceptionProperty {
    USER_NOT_FOUND(404, "User Not Found", "사용자를 찾을 수 없습니다."),
    EMAIL_ALREADY_EXISTS(409, "Email Already Exists", "이 이메일이 이미 사용중입니다."),
    INVALID_OAUTH_TYPE(400, "Invalid OAuth Token", "지원되지 않는 OAuth 종류입니다.")
}