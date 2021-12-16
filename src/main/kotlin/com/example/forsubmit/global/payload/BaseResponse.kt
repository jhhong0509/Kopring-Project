package com.example.forsubmit.global.payload

import com.example.forsubmit.global.exception.GlobalException

class BaseResponse<T>(
    val status: Int,
    val message: String,
    val koreanMessage: String,
    val content: T
) {
    override fun toString(): String {
        return """
            {
                "status": ${status},
                "message": "$message",
                "koreanMessage": "$koreanMessage"
            }
        """.trimIndent()
    }

    companion object {
        fun of(exception: GlobalException): BaseResponse<Unit> {
            return BaseResponse(
                status = exception.status,
                message = exception.errorMessage,
                koreanMessage = exception.koreanMessage,
                Unit
            )
        }
    }
}