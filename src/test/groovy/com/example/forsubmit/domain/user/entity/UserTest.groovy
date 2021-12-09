package com.example.forsubmit.domain.user.entity

import com.example.forsubmit.JpaConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import spock.lang.Specification

@Import(JpaConfig)
@DataJpaTest
class UserTest extends Specification {

    @Autowired
    private TestEntityManager entityManager

    def "Save Success Test"() {
        when:
        def unsaved = new User(0, "email@dsm.hs.kr", "name", "passowrd", new ArrayList(), new ArrayList(), new ArrayList())
        def user = entityManager.persistAndFlush(unsaved)

        then:
        user.id != 0
    }

}
