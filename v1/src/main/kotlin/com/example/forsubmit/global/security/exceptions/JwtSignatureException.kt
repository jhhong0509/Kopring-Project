package com.example.forsubmit.global.security.exceptions

import com.example.forsubmit.global.exception.GlobalException

class JwtSignatureException private constructor() : GlobalException(AuthErrorCode.JWT_SIGNATURE){
    companion object {
        @JvmField
        val EXCEPTION = JwtSignatureException()
    }
}