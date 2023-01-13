package com.example.forsubmit.global.security.exceptions

import com.example.forsubmit.global.exception.GlobalException

class JwtValidateException private constructor() : GlobalException(AuthErrorCode.JWT_VALIDATE_FAIL) {
    companion object {
        @JvmField
        val EXCEPTION = JwtValidateException()
    }
}