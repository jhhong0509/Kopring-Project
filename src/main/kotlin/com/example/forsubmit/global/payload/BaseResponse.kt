package com.example.forsubmit.global.payload

import com.example.forsubmit.global.exception.GlobalException
import com.fasterxml.jackson.annotation.JsonInclude

class BaseResponse<T>(
    val status: Int,
    val message: String,
    val koreanMessage: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val content: T?
) {
    override fun toString(): String {
        return """
            {
                "status": ${status},
                "message": "$message",
                "korean_message": "$koreanMessage"
            }
        """.trimIndent()
    }

    companion object {
        fun of(exception: GlobalException): BaseResponse<Unit> {
            return BaseResponse(
                status = exception.status,
                message = exception.errorMessage,
                koreanMessage = exception.koreanMessage,
                content = null
            )
        }
    }
}