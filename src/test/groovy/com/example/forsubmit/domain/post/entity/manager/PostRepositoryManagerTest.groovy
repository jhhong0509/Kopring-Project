package com.example.forsubmit.domain.post.entity.manager

import com.example.forsubmit.domain.post.entity.Post
import com.example.forsubmit.domain.post.entity.PostRepository
import com.example.forsubmit.domain.post.exceptions.PostNotFoundException
import com.example.forsubmit.domain.user.entity.User
import spock.lang.Specification

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
}
