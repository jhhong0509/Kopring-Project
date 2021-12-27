package com.example.forsubmit.domain.user.service

import com.example.forsubmit.TestUtils
import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.entity.UserRepository
import com.example.forsubmit.domain.user.exceptions.EmailAlreadyExistsException
import com.example.forsubmit.domain.user.payload.request.SignUpRequest
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class UserServiceTest extends Specification {

    private UserRepository userRepository = GroovyMock(UserRepository)
    private JwtTokenProvider jwtTokenProvider = GroovyMock(JwtTokenProvider)
    private PasswordEncoder passwordEncoder = GroovyMock(PasswordEncoder)
    private UserService userService = new UserService(userRepository, jwtTokenProvider, passwordEncoder)

    def "User Service Save Test"() {
        given:
        def request = new SignUpRequest()
        TestUtils.setVariable("email", email, request)
        TestUtils.setVariable("password", password, request)
        TestUtils.setVariable("name", name, request)

        userRepository.findByEmail(_) >> {}
        jwtTokenProvider.getToken(_) >> new TokenResponse(accessToken, refreshToken)
        passwordEncoder.encode(_) >> "sadfadsf"

        when:
        def response = userService.saveUser(request)

        then:
        response.status == 201
        response.koreanMessage != null
        response.message != null
        response.content.accessToken != null
        response.content.refreshToken != null

        where:
        email               | name     | password    | accessToken       | refreshToken
        "email11@dsm.hs.kr" | "name11" | "password1" | "Bearer asdfasfd" | "Bearer asdfasdf"
        "email22@dsm.hs.kr" | "name22" | "password2" | "Bearer asdfasdf" | "Bearer asdfadsf"
    }

    def "User Service Save Test Failed - Email Already Exists"() {
        given:
        userRepository.findByNaturalId(_) >> new User()
        def request = new SignUpRequest()
        TestUtils.setVariable("email", email, request)
        TestUtils.setVariable("password", password, request)
        TestUtils.setVariable("name", name, request)

        when:
        userService.saveUser(request)

        then:
        thrown(EmailAlreadyExistsException)

        where:
        email               | name     | password
        "email11@dsm.hs.kr" | "name11" | "password1"
        "email22@dsm.hs.kr" | "name22" | "password2"
    }
}
