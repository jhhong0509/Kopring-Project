package com.example.forsubmit.domain.post.service

import com.example.forsubmit.TestUtils
import com.example.forsubmit.domain.post.entity.Post
import com.example.forsubmit.domain.post.entity.PostRepository
import com.example.forsubmit.domain.post.exceptions.CannotDeletePostException
import com.example.forsubmit.domain.post.exceptions.CannotUpdatePostException
import com.example.forsubmit.domain.post.exceptions.PostNotFoundException
import com.example.forsubmit.domain.post.payload.request.CreatePostRequest
import com.example.forsubmit.domain.post.payload.request.UpdatePostRequest
import com.example.forsubmit.domain.post.payload.response.PostPageResponse
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.facade.UserFacade
import spock.lang.Specification

import java.time.LocalDateTime

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
        postService.deletePost(id)

        then:
        thrown(CannotDeletePostException)

        where:
        id | _
        1  | _
        2  | _
    }

    def "Get Single Post Success Test"() {
        given:
        def user = new User(accountId, name, "password")
        def post = new Post(title, content, user)
        TestUtils.setVariable("createdDate", createdDate, post)
        userFacade.findCurrentUser() >> user
        postRepository.findById(id) >> Optional.of(post)

        when:
        def response = postService.getSinglePost(id)

        then:
        response.status == 200
        response.koreanMessage != null
        response.message != null
        response.content.title == title
        response.content.content == content
        response.content.createdAt == createdDate
        response.content.accountId == accountId
        response.content.userName == name

        where:
        id | title    | content    | accountId             | name   | createdDate
        1  | "title1" | "content1" | "accountId@dsm.hs.kr" | "name" | LocalDateTime.of(2021, 5, 9, 20, 5)
        2  | ""       | ""         | ""                    | ""     | LocalDateTime.now()
    }

    def "Get Single Post Test - Not Found"() {
        given:
        postRepository.findById(id) >> Optional.empty()

        when:
        postService.getSinglePost(id)

        then:
        thrown(PostNotFoundException)

        where:
        id | _
        1  | _
        2  | _
    }

    def "Get Post List Test"() {
        given:
        def user = new User(accountId, name, "password")
        def post = new Post(title, content, user)
        TestUtils.setVariable("createdDate", createdDate, post)
        postRepository.postPageable(_) >> new PostPageResponse(List.of(post, post, post, post), 1)

        when:
        def response = postService.getPostList(id)

        then:
        response.status == 200
        response.koreanMessage != null
        response.message != null
        response.content.nextId != null
        response.content.responses.last().id == 0
        response.content.responses.last().userName == name
        response.content.responses.last().createdAt == createdDate
        response.content.responses.last().accountId == accountId
        response.content.responses.last().title == title

        where:
        id | title    | content    | accountId   | name   | createdDate
        1  | "title1" | "content1" | "accountId" | "name" | LocalDateTime.of(2021, 5, 9, 20, 5)
        2  | ""       | ""         | ""          | ""     | LocalDateTime.now()
    }

}
