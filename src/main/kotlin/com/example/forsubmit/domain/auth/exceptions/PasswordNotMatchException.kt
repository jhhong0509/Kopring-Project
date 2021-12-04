package com.example.forsubmit.domain.auth.exceptions

import com.example.forsubmit.global.exception.GlobalException

class PasswordNotMatchException private constructor() : GlobalException(AuthErrorCode.PASSWORD_NOT_MATCH){
    companion object {
        @JvmField
        val EXCEPTION = PasswordNotMatchException()
    }
}