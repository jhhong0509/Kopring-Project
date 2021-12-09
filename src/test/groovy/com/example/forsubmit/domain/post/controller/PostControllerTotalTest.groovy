package com.example.forsubmit.domain.post.controller

import com.example.forsubmit.domain.post.entity.PostRepository
import com.example.forsubmit.domain.post.payload.request.CreatePostRequest
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.entity.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class PostControllerTotalTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private PostRepository postRepository

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    private UserRepository userRepository

    def cleanup() {
        postRepository.deleteAll()
        userRepository.deleteAll()
    }

    @WithMockUser(username = "email@dsm.hs.kr")
    def "Post Controller Test"() {
        setup:
        userRepository.save(new User(0, "email@dsm.hs.kr", "name", "password", new ArrayList(), new ArrayList(), new ArrayList()))
        def req = new CreatePostRequest(title, content)

        when:
        def response = mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isCreated())

        where:
        title    | content
        "title"  | "content"
        "title2" | "content2"
    }

    @WithMockUser(username = "email@dsm.hs.kr")
    def "Post Controller Bad Request Test"() {
        setup:
        userRepository.save(new User(0, "email@dsm.hs.kr", "name", "password", new ArrayList(), new ArrayList(), new ArrayList()))
        def req = new CreatePostRequest(title, "")

        when:
        def response = mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())

        where:
        title    | _
        "title"  | _
        "title2" | _
    }

    def "Post Controller Forbidden"() {
        def req = new CreatePostRequest("title", "content")

        mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())

    }

}
