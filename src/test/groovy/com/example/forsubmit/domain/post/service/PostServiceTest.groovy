package com.example.forsubmit.domain.post.service

import com.example.forsubmit.TestUtils
import com.example.forsubmit.domain.post.entity.Post
import com.example.forsubmit.domain.post.entity.PostRepository
import com.example.forsubmit.domain.post.payload.request.CreatePostRequest
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
        TestUtils.setVariable(CreatePostRequest.class, "title", title, postRequest)
        TestUtils.setVariable(CreatePostRequest.class, "content", content, postRequest)
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
        TestUtils.setVariable(CreatePostRequest.class, "title", title, postRequest)
        TestUtils.setVariable(CreatePostRequest.class, "content", content, postRequest)

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
}
