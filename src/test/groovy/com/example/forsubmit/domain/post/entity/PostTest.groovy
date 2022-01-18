package com.example.forsubmit.domain.post.entity

import com.example.forsubmit.BaseJpaTest
import com.example.forsubmit.domain.user.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

class PostTest extends BaseJpaTest {

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
