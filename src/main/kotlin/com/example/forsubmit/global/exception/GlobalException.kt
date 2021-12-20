package com.example.forsubmit.global.exception

import com.example.forsubmit.global.exception.property.ExceptionProperty

open class GlobalException(private val property: ExceptionProperty): RuntimeException(property.errorMessage) {
    val status: Int
    get() = property.status

    val errorMessage
    get() = property.errorMessage

    val koreanMessage
    get() = property.koreanMessage
}