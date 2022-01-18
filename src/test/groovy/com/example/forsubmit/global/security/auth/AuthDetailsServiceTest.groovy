package com.example.forsubmit.global.security.auth

import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.facade.UserFacade
import spock.lang.Specification

class AuthDetailsServiceTest extends Specification {
    private def userFacade = GroovyMock(UserFacade)

    private def authDetails = new AuthDetailsService(userFacade)

    def "User Facade Test"() {
        given:
        def user = new User(email, name, "password")
        userFacade.findUserByAccountId(_) >> user

        when:
        def userDetails = authDetails.loadUserByUsername(email)

        then:
        userDetails.username == email
        userDetails.password == name
        userDetails.accountNonExpired
        userDetails.accountNonLocked
        userDetails.credentialsNonExpired
        userDetails.enabled
        userDetails.authorities != null

        where:
        email             | name
        "email@dsm.hs.kr" | "name"
        "test"            | "namename"
    }
}
