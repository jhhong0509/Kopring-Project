package com.example.forsubmit.global.security.jwt

import com.example.forsubmit.domain.auth.payload.response.AccessTokenResponse
import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.global.security.auth.AuthDetailsService
import com.example.forsubmit.global.security.exceptions.JwtExpiredException
import com.example.forsubmit.global.security.exceptions.JwtSignatureException
import com.example.forsubmit.global.security.exceptions.JwtValidateException
import com.example.forsubmit.global.security.exceptions.UnexpectedTokenException
import com.example.forsubmit.global.security.property.JwtProperties
import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    private val authDetailsService: AuthDetailsService,
    private val jwtProperty: JwtProperties
) {

    fun authenticateUser(token: String): Authentication {
        val claims = getClaims(token)
        val id = claims.subject
        val authDetails = authDetailsService.loadUserByUsername(id.toString())
        return UsernamePasswordAuthenticationToken(authDetails, "", authDetails.authorities)
    }

    fun getToken(email: String): TokenResponse {
        val accessToken = generateToken(email, jwtProperty.accessTokenExp, JwtProperties.ACCESS_VALUE)
        val refreshToken = generateToken(email, jwtProperty.refreshTokenExp, JwtProperties.REFRESH_VALUE)
        return TokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun getAccessToken(email: String): AccessTokenResponse {
        val accessToken = generateToken(email, jwtProperty.accessTokenExp, JwtProperties.ACCESS_VALUE)
        return AccessTokenResponse(accessToken)
    }

    private fun generateToken(email: String, expiration: Long, type: String): String {
        return "Bearer " + Jwts.builder()
            .setSubject(email)
            .setIssuedAt(Date())
            .signWith(SignatureAlgorithm.HS512, jwtProperty.secretKey)
            .setExpiration(Date(System.currentTimeMillis() + expiration * 1000))
            .setHeaderParam("typ", type)
            .compact()
    }

    fun getTokenFromHeader(httpServletRequest: HttpServletRequest): String? =
        httpServletRequest.getHeader(JwtProperties.TOKEN_HEADER_NAME)

    fun parseToken(token: String): String {
        if (token.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return token.replace(JwtProperties.TOKEN_PREFIX, "")
        }
        throw JwtValidateException.EXCEPTION
    }

    private fun getClaims(token: String): Claims {
        return try {
            Jwts.parser().setSigningKey(jwtProperty.secretKey)
                .parseClaimsJws(token).body
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> throw JwtExpiredException.EXCEPTION
                is SignatureException -> throw JwtSignatureException.EXCEPTION
                is MalformedJwtException -> throw JwtValidateException.EXCEPTION
                else -> throw UnexpectedTokenException.EXCEPTION
            }
        }
    }
}