package com.example.forsubmit.domain.user.entity

import com.example.forsubmit.BaseJpaTest
import org.springframework.beans.factory.annotation.Autowired

class UserTest extends BaseJpaTest {

    @Autowired
    private UserRepository userRepository

    def "Save Success Test"() {
        when:
        def unsaved = new User("accountId@dsm.hs.kr", "name", "password")
        def user = userRepository.save(unsaved)

        then:
        user.id != 0
        user.name != null
        user.accountId != null
        user.password != null
        user.posts.isEmpty()

        where:
        accountId   | name    | password
        "accountId" | "name1" | "password"
        ""          | ""      | ""
    }

    def "Find By NaturalKey Test"() {
        given:
        def unsaved = new User(accountId, "name", "password")
        def user = userRepository.save(unsaved)

        when:
        def foundUser = userRepository.findByNaturalId(accountId)


        then:
        user.id != 0
        user.name != null

        where:
        accountId             | _
        "accountId@dsm.hs.kr" | _
        ""                    | _
    }

}
