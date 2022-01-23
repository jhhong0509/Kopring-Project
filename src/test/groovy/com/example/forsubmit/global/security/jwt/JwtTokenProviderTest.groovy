package com.example.forsubmit.global.security.jwt

import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.global.security.auth.AuthDetails
import com.example.forsubmit.global.security.auth.AuthDetailsService
import com.example.forsubmit.global.security.exceptions.JwtExpiredException
import com.example.forsubmit.global.security.exceptions.JwtSignatureException
import com.example.forsubmit.global.security.exceptions.JwtValidateException
import com.example.forsubmit.global.security.property.JwtProperties
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class JwtTokenProviderTest extends Specification {

    private JwtProperties jwtProperties = GroovyMock(JwtProperties)
    private AuthDetailsService authDetailsService = GroovyMock(AuthDetailsService)
    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(authDetailsService, jwtProperties)

    def "AuthenticateUser Success"() {
        given:
        jwtProperties.secretKey >> "asdfdsaf"
        jwtProperties.accessTokenExp >> exp
        def bearerToken = jwtTokenProvider.getAccessToken(accountId).accessToken
        def accessToken = jwtTokenProvider.parseToken(bearerToken)
        authDetailsService.loadUserByUsername(accountId) >> new AuthDetails(new User())

        when:
        jwtTokenProvider.authenticateUser(accessToken)

        then:
        noExceptionThrown()

        where:
        accountId    | exp
        "accountId1" | 10000
        "accountId2" | 1000000
    }

    def "AuthenticateUser Fail"() {
        given:
        jwtProperties.secretKey >> "asdfdsaf"
        jwtProperties.accessTokenExp >> exp
        def bearerToken = jwtTokenProvider.getAccessToken(accountId).accessToken
        def accessToken = prefix + jwtTokenProvider.parseToken(bearerToken) + postfix
        authDetailsService.loadUserByUsername(accountId) >> new AuthDetails(new User())

        when:
        jwtTokenProvider.authenticateUser(accessToken)

        then:
        thrown(exception)

        where:
        accountId | exp     | prefix     | postfix   | exception
        "sadf"    | 0       | ""         | ""        | JwtExpiredException
        "asdf"    | 1000000 | ""         | "kjhkjhk" | JwtSignatureException
        "dasf"    | 100000  | "asdfasdf" | ""        | JwtValidateException
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
        given:
        jwtProperties.accessTokenExp >> 1000
        jwtProperties.refreshTokenExp >> 200000
        jwtProperties.secretKey >> secretKey

        when:
        def response = jwtTokenProvider.getToken(accountId)

        then:
        response.accessToken != null
        response.refreshToken != null

        where:
        accountId  | secretKey
        "asdf"     | "secret"
        "dasfadsf" | "secret2"
    }

}
