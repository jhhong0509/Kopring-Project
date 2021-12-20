package com.example.forsubmit.domain.user.entity

import com.example.forsubmit.JpaConfig
import com.example.forsubmit.global.querydsl.QueryDSLConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import spock.lang.Specification

@Import([JpaConfig, QueryDSLConfig])
@DataJpaTest
class UserTest extends Specification {

    @Autowired
    private TestEntityManager entityManager

    def "Save Success Test"() {
        when:
        def unsaved = new User("email@dsm.hs.kr", "name", "passowrd")
        def user = entityManager.persistAndFlush(unsaved)

        then:
        user.id != 0
    }

}
