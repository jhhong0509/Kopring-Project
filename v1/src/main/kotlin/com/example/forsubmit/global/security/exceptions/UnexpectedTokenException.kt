package com.example.forsubmit.global.security.exceptions

import com.example.forsubmit.global.exception.GlobalException

class UnexpectedTokenException private constructor() : GlobalException(AuthErrorCode.UNEXPECTED_TOKEN){
    companion object {
        @JvmField
        val EXCEPTION = UnexpectedTokenException()
    }
}