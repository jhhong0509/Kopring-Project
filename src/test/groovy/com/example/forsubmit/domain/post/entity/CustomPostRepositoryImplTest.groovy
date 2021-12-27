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
class CustomPostRepositoryImplTest extends Specification {

    @Autowired
    private PostRepository postRepository

    @Autowired
    private TestEntityManager entityManager

    private User user

    def setup() {
        User user = new User("email@dsm.hs.kr", "name", "password")
        this.user = entityManager.persist(user)
    }

    def cleanup() {
        entityManager.detach(user)
        postRepository.deleteAll()
    }

    def "Post Pagination Test With NextPostId"() {
        given:
        Long nextId = savePost(size)

        when:
        def response = postRepository.postPageable(nextId)

        then:
        response.nextPostId != null
        for (def post : response.posts) {
            post.id != 0
            post.title != null
            post.content != null
            post.createdDate != null
            post.user != null
        }

        where:
        size | _
        20   | _
        30   | _
    }

    def "Post Pagination Test Without Next"() {
        given:
        Long nextId = savePost(size)

        when:
        def response = postRepository.postPageable(nextId)

        then:
        response.nextPostId == null
        for (def post : response.posts) {
            post.id != 0
            post.title != null
            post.content != null
            post.createdDate != null
            post.user != null
        }

        where:
        size | _
        9    | _
        10   | _
    }

    def "Post Pagination Test null"() {
        given:
        savePost(size)

        when:
        def response = postRepository.postPageable(null)

        then:
        for (def post : response.posts) {
            post.id != 0
            post.title != null
            post.content != null
            post.createdDate != null
            post.user != null
        }
        !response.posts.isEmpty()

        where:
        size | _
        10   | _
        30   | _
    }

    private Long savePost(int size) {
        Long nextId = 0
        for (i in 0..<size) {
            Post post = new Post("title", "content", user)
            Post savedPost = postRepository.save(post)
            nextId = savedPost.id
        }
        return nextId
    }
}
