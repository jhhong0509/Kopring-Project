package com.example.forsubmit.global.security

import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val bearerToken = jwtTokenProvider.getTokenFromHeader(request)
        bearerToken?.let {
            val token: String = jwtTokenProvider.parseToken(it)
            val authentication = jwtTokenProvider.authenticateUser(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response);
    }
}