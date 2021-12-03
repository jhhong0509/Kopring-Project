package com.example.forsubmit.global.security.exceptions

import com.example.forsubmit.global.exception.property.ExceptionProperty

enum class AuthErrorCode(
    override val status: Int,
    override val errorMessage: String,
    override val koreanMessage: String
) : ExceptionProperty {

    USER_NOT_FOUND(404, "User Not Found", "사용자를 찾을 수 없습니다."),
    JWT_EXPIRED(401, "Jwt Token Expired", "토큰이 만료되었습니다."),
    JWT_SIGNATURE(401, "Invalid Signature", "토큰 시그니쳐가 손상되어있습니다."),
    JWT_VALIDATE_FAIL(401, "Token Validate Failed", "토큰 검증에 실패했습니다."),
    UNEXPECTED_TOKEN(500, "Unexpected Token Exception", "토큰 검증 과정에서 예기치 못한 문제가 발생했습니다.")

}