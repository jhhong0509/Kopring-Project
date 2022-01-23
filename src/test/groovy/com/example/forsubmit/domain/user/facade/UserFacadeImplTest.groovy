package com.example.forsubmit.domain.user.facade

import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.entity.UserRepository
import com.example.forsubmit.domain.user.exceptions.UserNotFoundException
import com.example.forsubmit.global.security.auth.AuthDetails
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
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

    def "userFacade findByAccountId test"() {
        given:
        userRepository.findByAccountId(_) >> new User()

        when:
        userFacade.findUserByAccountId(accountId)

        then:
        noExceptionThrown()

        where:
        accountId             | _
        "accountId@dsm.hs.kr" | _
        ""                    | _
    }

    def "userFacade findByAccountId Not Found test"() {
        when:
        userFacade.findUserByAccountId(accountId)

        then:
        thrown(UserNotFoundException)

        where:
        accountId   | _
        "accountId" | _
        ""          | _
    }

    def "userFacade findCurrentUser Test"() {
        given:
        def user = new User()
        def authDetails = new AuthDetails(user)
        SecurityContextHolder.context.authentication >> new UsernamePasswordAuthenticationToken(authDetails, "", new ArrayList())
        userRepository.findByAccountId(_) >> user

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