package com.example.forsubmit.domain.post.service

import com.example.forsubmit.TestUtils
import com.example.forsubmit.domain.post.entity.Post
import com.example.forsubmit.domain.post.entity.PostRepository
import com.example.forsubmit.domain.post.exceptions.CannotDeletePostException
import com.example.forsubmit.domain.post.exceptions.CannotUpdatePostException
import com.example.forsubmit.domain.post.payload.request.CreatePostRequest
import com.example.forsubmit.domain.post.payload.request.UpdatePostRequest
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.facade.UserFacade
import spock.lang.Specification

class PostServiceTest extends Specification {

    private def postRepository = GroovyMock(PostRepository)
    private def userFacade = GroovyMock(UserFacade)
    private def postService = new PostService(postRepository, userFacade)

    def "Save Post Success Test"() {
        given:
        def postRequest = new CreatePostRequest()
        TestUtils.setVariable("title", title, postRequest)
        TestUtils.setVariable("content", content, postRequest)
        def user = new User()
        userFacade.findCurrentUser() >> user
        postRepository.save(_) >> new Post(postRequest.title, postRequest.content, user)

        when:
        def response = postService.savePost(postRequest)

        then:
        response.status == 201
        response.koreanMessage != null
        response.message != null
        response.content.id == 0

        where:
        title     | content
        "title1"  | "content1"
        "title22" | "content22"
    }

    def "Save Post Fail Test"() {
        given:
        def postRequest = new CreatePostRequest()
        TestUtils.setVariable("title", title, postRequest)
        TestUtils.setVariable("content", content, postRequest)

        def user = new User()
        userFacade.findCurrentUser() >> user
        postRepository.save(_) >> new Post(postRequest.title, postRequest.content, user)

        when:
        def response = postService.savePost(postRequest)

        then:
        response.status == 201
        response.koreanMessage != null
        response.message != null
        response.content.id == 0

        where:
        title     | content
        "title1"  | "content1"
        "title22" | "content22"
    }

    def "Update Post Success Test"() {
        given:
        def postRequest = new UpdatePostRequest()
        TestUtils.setVariable("title", title, postRequest)
        TestUtils.setVariable("content", content, postRequest)

        def user = new User()
        def post = new Post("", "", user)
        userFacade.findCurrentUser() >> user
        postRepository.findById(id) >> Optional.of(post)

        when:
        def response = postService.updatePost(id, postRequest)

        then:
        response.status == 200
        response.koreanMessage != null
        response.message != null

        where:
        id | title     | content
        1  | "title1"  | "content1"
        2  | "title22" | "content22"
    }

    def "Update Post Fail Test - 403"() {
        given:
        def postRequest = new UpdatePostRequest()
        TestUtils.setVariable("title", title, postRequest)
        TestUtils.setVariable("content", content, postRequest)

        def post = new Post("", "", new User())
        userFacade.findCurrentUser() >> new User()
        postRepository.findById(id) >> Optional.of(post)

        when:
        postService.updatePost(id, postRequest)

        then:
        thrown(CannotUpdatePostException)

        where:
        id | title     | content
        1  | "title1"  | "content1"
        2  | "title22" | "content22"
    }

    def "Delete Post Success Test"() {
        given:
        def user = new User()
        def post = new Post("", "", user)
        userFacade.findCurrentUser() >> user
        postRepository.findById(id) >> Optional.of(post)

        when:
        def response = postService.deletePost(id)

        then:
        response.status == 200
        response.koreanMessage != null
        response.message != null

        where:
        id | _
        1  | _
        2  | _
    }

    def "Delete Post Fail Test"() {
        given:
        def post = new Post("", "", new User())
        userFacade.findCurrentUser() >> new User()
        postRepository.findById(id) >> Optional.of(post)

        when:
        def response = postService.deletePost(id)

        then:
        thrown(CannotDeletePostException)

        where:
        id | _
        1  | _
        2  | _
    }


}
