package com.example.forsubmit.domain.user.service

import com.example.forsubmit.TestUtils
import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.exceptions.AccountIdAlreadyExistsException
import com.example.forsubmit.domain.user.facade.UserFacade
import com.example.forsubmit.domain.user.payload.request.SignUpRequest
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class UserServiceTest extends Specification {

    private UserFacade userFacade = GroovyMock(UserFacade)
    private JwtTokenProvider jwtTokenProvider = GroovyMock(JwtTokenProvider)
    private PasswordEncoder passwordEncoder = GroovyMock(PasswordEncoder)
    private UserService userService = new UserService(userFacade, jwtTokenProvider, passwordEncoder)

    def "User Service Save Test"() {
        given:
        def request = setSignUpRequest(accountId, password, name)

        userFacade.findUserByAccountId(_) >> {}
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
        accountId     | name     | password    | accessToken       | refreshToken
        "accountId11" | "name11" | "password1" | "Bearer asdfasfd" | "Bearer asdfasdf"
        "accountId22" | "name22" | "password2" | "Bearer asdfasdf" | "Bearer asdfadsf"
    }

    def "User Service Save Test Failed - AccountId Already Exists"() {
        given:
        userFacade.findUserByAccountId(_) >> new User()
        def request = setSignUpRequest(accountId, password, name)

        passwordEncoder.encode(_) >> "asdf"
        userFacade.saveUser(_) >> { throw AccountIdAlreadyExistsException.EXCEPTION }

        when:
        userService.saveUser(request)

        then:
        thrown(AccountIdAlreadyExistsException)

        where:
        accountId    | name     | password
        "accountId1" | "name11" | "password1"
        "accountId2" | "name22" | "password2"
    }

    private def setSignUpRequest(String email, String password, String name) {
        SignUpRequest request = new SignUpRequest()

        TestUtils.setVariable("accountId", email, request)
        TestUtils.setVariable("password", password, request)
        TestUtils.setVariable("name", name, request)

        return request
    }

}
