package com.example.forsubmit.domain.post.entity.manager

import com.example.forsubmit.domain.post.entity.Post
import com.example.forsubmit.domain.post.entity.PostRepository
import com.example.forsubmit.domain.post.exceptions.PostNotFoundException
import com.example.forsubmit.domain.user.entity.User
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("test")
class PostRepositoryManagerTest extends Specification {

    def postRepository = GroovyMock(PostRepository)
    def postRepositoryManager = new PostRepositoryManager(postRepository)

    def "Save Post Success Test"() {
        given:
        def user = new User()
        def post = new Post(0, title, content, null, user)

        when:
        postRepositoryManager.save(post)

        then:
        postRepository.save(post) >> post
        noExceptionThrown()

        where:
        title    | content
        "title"  | "content"
        "title2" | "content2"
    }

    def "find Post Success Test"() {
        given:
        def user = new User()
        def post = new Post(id, "title", "content", null, user)

        when:
        def foundPost = postRepositoryManager.findById(id)

        then:
        postRepository.findById(id) >> Optional.of(post)
        foundPost.id == id

        where:
        id | _
        1  | _
        2  | _
    }

    def "find Post Failed Test"() {
        when:
        postRepositoryManager.findById(id)

        then:
        postRepository.findById(id) >> { throw PostNotFoundException.EXCEPTION }
        thrown(PostNotFoundException)

        where:
        id | _
        1  | _
        2  | _
    }

    def "Delete Post Success"() {
        given:
        def user = new User()
        def post = new Post(id, "title", "content", null, user)

        when:
        postRepositoryManager.delete(post)

        then:
        postRepository.delete(post)
        noExceptionThrown()

        where:
        id | _
        1  | _
        2  | _
    }

    def "Get Post Page Success"() {
        given:
        def pageRequest = PageRequest.of(page, size)
        def user = new User()
        def post1 = new Post(1, "title", "content", null, user)
        def post2 = new Post(2, "title", "content", null, user)
        def post3 = new Post(3, "title", "content", null, user)
        def post4 = new Post(4, "title", "content", null, user)

        def postList = List.of(post1, post2, post3, post4)

        when:
        def pageResponse = postRepositoryManager.findAllPagination(pageRequest)

        then:
        postRepository.findAll(pageRequest) >> postList
        noExceptionThrown()

        where:
        page | size
        0    | 2
        2    | 1
    }

}
