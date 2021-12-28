package com.example.forsubmit.domain.user.facade

import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.entity.UserRepository
import com.example.forsubmit.domain.user.exceptions.UserNotFoundException
import com.example.forsubmit.global.security.auth.AuthDetails
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

class UserFacadeImplTest extends Specification {

    private def userRepository = GroovyMock(UserRepository)
    private def userFacade = new UserFacadeImpl(userRepository)

    def "userFacade findById success test"() {
        given:
        userRepository.findById(_) >> Optional.of(new User())

        when:
        userFacade.findUserById(id)

        then:
        noExceptionThrown()

        where:
        id | _
        1  | _
        0  | _
    }

    def "userFacade findById not found test"() {
        given:
        userRepository.findById(_) >> Optional.empty()

        when:
        userFacade.findUserById(id)

        then:
        thrown(UserNotFoundException)

        where:
        id | _
        1  | _
        0  | _
    }

    def "userFacade findByEmail test"() {
        given:
        userRepository.findByEmail(_) >> new User()

        when:
        userFacade.findUserByEmail(email)

        then:
        noExceptionThrown()

        where:
        email             | _
        "email@dsm.hs.kr" | _
        ""                | _
    }

    def "userFacade findByEmail Not Found test"() {
        when:
        userFacade.findUserByEmail(email)

        then:
        thrown(UserNotFoundException)

        where:
        email             | _
        "email@dsm.hs.kr" | _
        ""                | _
    }

    def "userFacade findCurrentUser Test"() {
        given:
        def user = new User()
        def authDetails = new AuthDetails(user)
        SecurityContextHolder.context.authentication >> new UsernamePasswordAuthenticationToken(authDetails, "", new ArrayList())
        userRepository.findByEmail(_) >> user

        when:
        userFacade.findCurrentUser()

        then:
        thrown(UserNotFoundException)
    }

    def "userFacade findCurrentUser Not Found Test"() {
        when:
        userFacade.findCurrentUser()

        then:
        thrown(UserNotFoundException)
    }
}