package com.example.forsubmit.domain.user.exceptions

import com.example.forsubmit.global.exception.property.ExceptionProperty

enum class UserErrorCode(
    override val status: Int,
    override val errorMessage: String,
    override val koreanMessage: String
): ExceptionProperty {
    USER_NOT_FOUND(404, "User Not Found", "사용자를 찾을 수 없습니다."),
    ACCOUNT_ID_ALREADY_EXISTS(409, "AccountId Already Exists", "이 계정이 이미 사용중입니다.")
}