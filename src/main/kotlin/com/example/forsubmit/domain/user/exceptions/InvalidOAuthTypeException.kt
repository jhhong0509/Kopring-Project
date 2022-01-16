package com.example.forsubmit.domain.user.exceptions

import com.example.forsubmit.global.exception.GlobalException

class InvalidOAuthTypeException private constructor() : GlobalException(UserErrorCode.INVALID_OAUTH_TYPE) {
    companion object {
        @JvmField
        val EXCEPTION = InvalidOAuthTypeException()
    }
}