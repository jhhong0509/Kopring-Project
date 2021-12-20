package com.example.forsubmit.global.security.exceptions

import com.example.forsubmit.global.exception.GlobalException

class ForbiddenException private constructor() : GlobalException(AuthErrorCode.FORBIDDEN) {
    companion object {
        @JvmField
        val EXCEPTION = ForbiddenException()
    }
}