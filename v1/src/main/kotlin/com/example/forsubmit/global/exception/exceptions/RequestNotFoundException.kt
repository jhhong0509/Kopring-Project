package com.example.forsubmit.global.exception.exceptions

import com.example.forsubmit.global.exception.GlobalException
import com.example.forsubmit.global.exception.property.GlobalExceptionErrorCode

class RequestNotFoundException private constructor(): GlobalException(GlobalExceptionErrorCode.REQUEST_NOT_FOUND){
    companion object {
        @JvmField
        val EXCEPTION = RequestNotFoundException()
    }
}