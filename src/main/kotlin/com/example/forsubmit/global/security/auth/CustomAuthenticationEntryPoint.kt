package com.example.forsubmit.global.security.auth

import com.example.forsubmit.global.payload.BaseResponse
import com.example.forsubmit.global.security.exceptions.ForbiddenException
import com.example.forsubmit.global.security.exceptions.NoTokenPresentException
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val errorResponse = BaseResponse.of(NoTokenPresentException.EXCEPTION)

        response.characterEncoding = "UTF-8"
        response.status = errorResponse.status
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(errorResponse.toString())
    }

}