package com.example.forsubmit.domain.user.exceptions

import com.example.forsubmit.global.exception.GlobalException

class EmailAlreadyExistsException private constructor() : GlobalException(UserErrorCode.EMAIL_ALREADY_EXISTS) {
    companion object {
        @JvmField
        val EXCEPTION = EmailAlreadyExistsException()
    }
}