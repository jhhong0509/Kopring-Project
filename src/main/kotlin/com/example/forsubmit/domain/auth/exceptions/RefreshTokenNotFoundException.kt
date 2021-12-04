package com.example.forsubmit.domain.auth.exceptions

import com.example.forsubmit.global.exception.GlobalException

class RefreshTokenNotFoundException private constructor() : GlobalException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND) {
    companion object {
        @JvmField
        val EXCEPTION = RefreshTokenNotFoundException()
    }
}