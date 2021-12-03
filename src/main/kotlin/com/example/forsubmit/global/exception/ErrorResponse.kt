package com.example.forsubmit.global.exception

import com.example.forsubmit.global.exception.property.ExceptionProperty

class ErrorResponse(
    val status: Int,
    private val errorMessage: String,
    private val koreanMessage: String
) {
    override fun toString(): String {
        return """
            {
                "status": ${status},
                "errorMessage": "$errorMessage",
                "koreanMessage": "$koreanMessage"
            }
        """.trimIndent()
    }

    companion object {
        fun of(exception: GlobalException): ErrorResponse {
            return ErrorResponse(
                status = exception.status,
                errorMessage = exception.errorMessage,
                koreanMessage = exception.koreanMessage
            )
        }
    }
}