package com.example.forsubmit.global.exception.exceptions

import com.example.forsubmit.global.exception.GlobalException
import com.example.forsubmit.global.exception.property.GlobalExceptionErrorCode

class InternalServerError private constructor() : GlobalException(GlobalExceptionErrorCode.UNEXPECTED) {
    companion object {
        @JvmField
        val EXCEPTION = InternalServerError()
    }
}