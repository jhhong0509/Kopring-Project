package com.example.forsubmit.domain.user.entity

import com.example.forsubmit.BaseJpaTest
import org.springframework.beans.factory.annotation.Autowired

class UserTest extends BaseJpaTest {

    @Autowired
    private UserRepository userRepository

    def "Save Success Test"() {
        when:
        def unsaved = new User("email@dsm.hs.kr", "name", "password")
        def user = userRepository.save(unsaved)

        then:
        user.id != 0
        user.name != null
        user.email != null
        user.password != null
        user.posts.isEmpty()

        where:
        email             | name    | password
        "email@dsm.hs.kr" | "name1" | "password"
        ""                | ""      | ""
    }

    def "Find By NaturalKey Test"() {
        given:
        def unsaved = new User(email, "name", "password")
        def user = userRepository.save(unsaved)

        when:
        def foundUser = userRepository.findByNaturalId(email)


        then:
        user.id != 0
        user.name != null

        where:
        email             | _
        "email@dsm.hs.kr" | _
        ""                | _
    }

}
