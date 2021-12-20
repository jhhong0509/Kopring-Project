package com.example.forsubmit.domain.post.entity

import com.example.forsubmit.JpaConfig
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.global.querydsl.QueryDSLConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import spock.lang.Specification

@Import([JpaConfig, QueryDSLConfig])
@DataJpaTest
class PostTest extends Specification {

    @Autowired
    private TestEntityManager entityManager

    def "Save Success Test"() {
        given:
        def unsavedUser = new User("email", "name", "password")
        def user = entityManager.persist(unsavedUser)

        def unsavedPost = new Post("title", "content", user)

        when:
        def post = entityManager.persist(unsavedPost)

        then:
        post.id != 0
        post.title != null
        post
        post.user != null
    }
}
