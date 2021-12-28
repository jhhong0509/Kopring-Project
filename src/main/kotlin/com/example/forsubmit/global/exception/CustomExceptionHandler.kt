package com.example.forsubmit.global.exception

import com.example.forsubmit.global.exception.exceptions.InvalidMethodArgumentException
import com.example.forsubmit.global.exception.exceptions.RequestNotFoundException
import com.example.forsubmit.global.payload.BaseResponse
import mu.KLogger
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.NoHandlerFoundException

private val kLogger: KLogger = KotlinLogging.logger {}

@ControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentValid(): ResponseEntity<BaseResponse<Unit>> {
        return handleException(InvalidMethodArgumentException.EXCEPTION)
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun notFound(): ResponseEntity<BaseResponse<Unit>> {
        return handleException(RequestNotFoundException.EXCEPTION)
    }

    @ExceptionHandler(GlobalException::class)
    fun globalException(e: GlobalException): ResponseEntity<BaseResponse<Unit>> {
        return handleException(e)
    }

    private fun handleException(e: GlobalException): ResponseEntity<BaseResponse<Unit>> {
        kLogger.error { "Error Occurred: ${e.errorMessage}" }
        val httpStatus = HttpStatus.valueOf(e.status)
        val body = BaseResponse.of(e)
        return ResponseEntity(body, httpStatus)
    }
}