package com.example.forsubmit.domain.auth.service

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
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class AuthServiceTest extends Specification {

    def userFacade = Mock(UserFacade)
    def passwordEncoder = Mock(PasswordEncoder)
    def jwtTokenProvider = Mock(JwtTokenProvider)
    def refreshTokenRepository = Mock(RefreshTokenRepository)
    def authService = new AuthService(refreshTokenRepository, jwtTokenProvider, userFacade, passwordEncoder)

    def "Sign in Success test"() {
        given:
        def request = new AuthRequest("email@dsm.hs.kr", "password")
        def user = new User(1, request.email, "name", request.password)

        when:
        def response = authService.signIn(request)

        then:
        1 * userFacade.findUserByEmail(request.email) >> { user }
        1 * passwordEncoder.matches(request.password, user.password) >> true
        1 * jwtTokenProvider.getToken(user.id) >> { new TokenResponse(accessToken, refreshToken) }

        response.accessToken == accessToken
        response.refreshToken == refreshToken

        where:
        accessToken | refreshToken
        "1234"      | "5678"
    }

    def "Sign In Password Not Match Exception Test"() {
        given:
        def request = new AuthRequest("email@dsm.hs.kr", "password")
        def user = new User(1, request.email, "name", request.password)

        when:
        authService.signIn(request)

        then:
        1 * userFacade.findUserByEmail(request.email) >> { user }
        1 * passwordEncoder.matches(request.password, user.password) >> false

        thrown(PasswordNotMatchException)

    }

    def "Sign In User Not Found Exception Test"() {
        given:
        AuthRequest request = new AuthRequest(email, password)

        when:
        authService.signIn(request)

        then:
        userFacade.findUserByEmail(request.email) >> { throw UserNotFoundException.EXCEPTION }
        thrown(UserNotFoundException)

        where:
        email              | password
        "email@dsm.hs.kr"  | "password"
        "email2@dsm.hs.kr" | "password22"
    }

    def "Token Refresh Success Test"() {
        given:
        def user = new User(1, "email", "name", "password")

        when:
        def accessToken = authService.tokenRefresh(refreshToken)

        then:
        refreshTokenRepository.findByToken(refreshToken) >> new RefreshToken(1, refreshToken)
        jwtTokenProvider.getAccessToken(user.id) >> new AccessTokenResponse(expectedAccessToken)

        accessToken.getAccessToken() === expectedAccessToken

        where:
        refreshToken | expectedAccessToken
        "testtest"   | "result1"
        "testtest2"  | "result2"
    }

    def "Token Refresh Not Found"() {
        when:
        authService.tokenRefresh(refreshToken)

        then:
        refreshTokenRepository.findByToken(refreshToken) >> { throw RefreshTokenNotFoundException.EXCEPTION }
        thrown(RefreshTokenNotFoundException)

        where:
        refreshToken | _
        "testtest"   | _
        "testtest2"  | _
    }

}
