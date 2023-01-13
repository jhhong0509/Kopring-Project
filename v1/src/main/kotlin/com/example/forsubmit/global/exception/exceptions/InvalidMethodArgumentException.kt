package com.example.forsubmit.global.exception.exceptions

import com.example.forsubmit.global.exception.GlobalException
import com.example.forsubmit.global.exception.property.GlobalExceptionErrorCode

class InvalidMethodArgumentException private constructor(): GlobalException(GlobalExceptionErrorCode.INVALID_METHOD_ARGUMENT){
    companion object {
        @JvmField
        val EXCEPTION = InvalidMethodArgumentException()
    }
}