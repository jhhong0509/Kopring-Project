package com.example.forsubmit.domain.auth.exceptions

import com.example.forsubmit.global.exception.GlobalException

class InvalidOauthTypeException private constructor() : GlobalException(AuthErrorCode.INVALID_OAUTH_TYPE){
    companion object {
        @JvmField
        val EXCEPTION = InvalidOauthTypeException()
    }
}