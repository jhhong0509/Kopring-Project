package com.example.forsubmit.domain.auth.service

import com.example.forsubmit.TestUtils
import com.example.forsubmit.domain.auth.entity.RefreshToken
import com.example.forsubmit.domain.auth.entity.RefreshTokenRepository
import com.example.forsubmit.domain.auth.exceptions.PasswordNotMatchException
import com.example.forsubmit.domain.auth.exceptions.RefreshTokenNotFoundException
import com.example.forsubmit.domain.auth.payload.request.AuthRequest
import com.example.forsubmit.domain.auth.payload.response.AccessTokenResponse
import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.exceptions.UserNotFoundException
import com.example.forsubmit.domain.user.facade.UserFacade
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import com.example.forsubmit.global.security.property.JwtProperties
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("test")
class AuthServiceTest extends Specification {

    private def userFacade = GroovyMock(UserFacade)
    private def passwordEncoder = GroovyMock(PasswordEncoder)
    private def jwtTokenProvider = GroovyMock(JwtTokenProvider)
    private def refreshTokenRepository = GroovyMock(RefreshTokenRepository)
    private def jwtProperties = GroovyMock(JwtProperties)
    private def authService = new AuthService(refreshTokenRepository, jwtTokenProvider, userFacade, passwordEncoder, jwtProperties)

    def "Sign in Success test"() {
        given:
        def request = new AuthRequest()
        TestUtils.setVariable("email", "email@dsm.hs.kr", request)
        TestUtils.setVariable("password", "password", request)
        def user = new User(request.email, "name", request.password)

        when:
        def response = authService.signIn(request)

        then:
        jwtProperties.refreshTokenExp >> 10000
        1 * userFacade.findUserByAccountId(request.email) >> { user }
        1 * passwordEncoder.matches(request.password, user.password) >> true
        1 * jwtTokenProvider.getToken(user.accountId) >> { new TokenResponse(accessToken, refreshToken) }

        response.content.accessToken == accessToken
        response.content.refreshToken == refreshToken

        where:
        accessToken | refreshToken
        "1234"      | "5678"
    }

    def "Sign In Password Not Match Exception Test"() {
        given:
        def request = new AuthRequest()
        TestUtils.setVariable("email", "email@dsm.hs.kr", request)
        TestUtils.setVariable("password", "password", request)
        def user = new User(request.email, "name", request.password)

        when:
        authService.signIn(request)

        then:
        1 * userFacade.findUserByAccountId(request.email) >> { user }
        1 * passwordEncoder.matches(request.password, request.password) >> false

        thrown(PasswordNotMatchException)

    }

    def "Sign In User Not Found Exception Test"() {
        given:
        def request = new AuthRequest()
        TestUtils.setVariable("email", "email@dsm.hs.kr", request)
        TestUtils.setVariable("password", "password", request)

        when:
        authService.signIn(request)

        then:
        userFacade.findUserByAccountId(request.email) >> { throw UserNotFoundException.EXCEPTION }
        thrown(UserNotFoundException)

        where:
        email              | password
        "email@dsm.hs.kr"  | "password"
        "email2@dsm.hs.kr" | "password22"
    }

    def "Token Refresh Success Test"() {
        given:
        def user = new User("email", "name", "password")

        when:
        def accessToken = authService.tokenRefresh(refreshToken)

        then:
        refreshTokenRepository.findByToken(refreshToken) >> new RefreshToken("email", refreshToken, 100000)
        jwtTokenProvider.getAccessToken(user.accountId) >> new AccessTokenResponse(expectedAccessToken)

        accessToken.content.accessToken === expectedAccessToken

        where:
        refreshToken | expectedAccessToken
        "testtest"   | "result1"
        "testtest2"  | "result2"
    }

    def "Token Refresh Not Found"() {
        when:
        authService.tokenRefresh(refreshToken)

        then:
        refreshTokenRepository.findByToken(refreshToken) >> null
        thrown(RefreshTokenNotFoundException)

        where:
        refreshToken | _
        "testtest"   | _
        "testtest2"  | _
    }

}
