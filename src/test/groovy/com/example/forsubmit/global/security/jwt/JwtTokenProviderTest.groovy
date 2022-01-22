package com.example.forsubmit.global.security.jwt

import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.global.security.auth.AuthDetails
import com.example.forsubmit.global.security.auth.AuthDetailsService
import com.example.forsubmit.global.security.exceptions.JwtSignatureException
import com.example.forsubmit.global.security.exceptions.JwtValidateException
import com.example.forsubmit.global.security.property.JwtProperties
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class JwtTokenProviderTest extends Specification {

    private JwtProperties jwtProperties = new JwtProperties("asdf", 1000, 100000)
    private AuthDetailsService authDetailsService = GroovyMock(AuthDetailsService)
    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(authDetailsService, jwtProperties)

    def "AuthenticateUser Success"() {
        given:
        def bearerToken = jwtTokenProvider.getAccessToken(email).accessToken
        def accessToken = jwtTokenProvider.parseToken(bearerToken)
        authDetailsService.loadUserByUsername(email) >> new AuthDetails(new User("email", "name", "password"))

        when:
        jwtTokenProvider.authenticateUser(accessToken)

        then:
        noExceptionThrown()

        where:
        email          | _
        "email2"       | _
        "asdfadsfadsf" | _
    }

    def "AuthenticateUser Fail"() {
        given:
        jwtProperties.accessTokenExp >> exp
        def bearerToken = jwtTokenProvider.getAccessToken(email).accessToken
        def accessToken = prefix + jwtTokenProvider.parseToken(bearerToken) + postfix
        authDetailsService.loadUserByUsername(email) >> new AuthDetails(new User())

        when:
        jwtTokenProvider.authenticateUser(accessToken)

        then:
        thrown(exception)

        where:
        email    | exp     | prefix     | postfix   | exception
        "email3" | 1000000 | ""         | "kjhkjhk" | JwtSignatureException
        "email"  | 100000  | "asdfasdf" | ""        | JwtValidateException
    }

    def "GetTokenFromHeader"() {
        given:
        def request = GroovyMock(HttpServletRequest)
        request.getHeader(JwtProperties.TOKEN_HEADER_NAME) >> token

        when:
        def headerToken = jwtTokenProvider.getTokenFromHeader(request)

        then:
        headerToken == token

        where:
        token  | _
        "asdf" | _
        "asas" | _
    }

    def "ParseTokenFail"() {
        when:
        jwtTokenProvider.parseToken("Not Starts With Bearer")

        then:
        thrown(JwtValidateException)
    }

    def "GetTokenSuccess"() {
        when:
        def response = jwtTokenProvider.getToken(email)

        then:
        response.accessToken != null
        response.refreshToken != null

        where:
        email    | secretKey
        "email1" | "secret"
        "email2" | "secret2"
    }

}
