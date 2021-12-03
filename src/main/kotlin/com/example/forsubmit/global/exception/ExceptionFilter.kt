package com.example.forsubmit.global.exception

import com.example.forsubmit.global.exception.exceptions.InternalServerError
import com.example.forsubmit.global.exception.exceptions.InvalidMethodArgumentException
import com.example.forsubmit.global.exception.exceptions.RequestNotFoundException
import org.springframework.http.MediaType
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ExceptionFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (exception: Exception) {
            when (val causeException = exception.cause) {
                is GlobalException -> writeErrorCode(causeException, response)
                is MethodArgumentTypeMismatchException,
                is MethodArgumentNotValidException -> writeErrorCode(InvalidMethodArgumentException.EXCEPTION, response)
                is NoHandlerFoundException -> writeErrorCode(RequestNotFoundException.EXCEPTION, response)
                else -> writeErrorCode(InternalServerError.EXCEPTION, response)
            }
        }
    }

    private fun writeErrorCode(exception: GlobalException, response: HttpServletResponse) {
        val errorResponse = ErrorResponse.of(exception)

        response.characterEncoding = "UTF-8"
        response.status = errorResponse.status
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(errorResponse.toString())

    }
}