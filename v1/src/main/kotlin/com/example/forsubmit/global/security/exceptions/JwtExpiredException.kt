package com.example.forsubmit.global.security.exceptions

import com.example.forsubmit.global.exception.GlobalException

class JwtExpiredException private constructor() : GlobalException(AuthErrorCode.JWT_EXPIRED) {
    companion object {
        @JvmField
        val EXCEPTION = JwtExpiredException()
    }
}