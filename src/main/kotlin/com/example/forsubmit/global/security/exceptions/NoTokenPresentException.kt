package com.example.forsubmit.global.security.exceptions

import com.example.forsubmit.global.exception.GlobalException

class NoTokenPresentException private constructor() : GlobalException(AuthErrorCode.NO_TOKEN) {
    companion object {
        @JvmField
        val EXCEPTION = NoTokenPresentException()
    }
}