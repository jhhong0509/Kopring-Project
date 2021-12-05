package com.example.forsubmit.domain.auth.controller

import com.example.forsubmit.domain.auth.payload.request.AuthRequest
import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.auth.service.AuthService
import com.example.forsubmit.domain.chat.entity.chat.Chat
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.entity.UserRepository
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private JwtTokenProvider jwtTokenProvider

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    private UserRepository userRepository

    @Autowired
    private PasswordEncoder passwordEncoder

    @Autowired
    private AuthService authService

    def cleanup() {
        userRepository.deleteAll()
    }

    def "Sign In Success"() {
        given:
        def request = new AuthRequest(email, requestPassword)
        def requestString = objectMapper.writeValueAsString(request)

        def user = new User(1, email, "name", passwordEncoder.encode(password), new ArrayList(), new ArrayList(), new ArrayList())
        userRepository.save(user)

        when:
        def result = mockMvc.perform(post("/auth")
                .content(requestString)
                .contentType(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(MockMvcResultMatchers.status().isCreated())

        def response = result.andReturn()
                .response.contentAsString
        def tokens = objectMapper.readValue(response, TokenResponse)

        tokens.refreshToken != null
        tokens.accessToken != null

        where:
        email             | requestPassword | password
        "email@dsm.hs.kr" | "password2"     | "password2"
        "email@dsm.hs.kr" | "password"      | "password"
    }

    def "Sign In Fail"() {
        given:
        def request = new AuthRequest(email, password)
        def requestString = objectMapper.writeValueAsString(request)

        when:
        def result = mockMvc.perform(post("/auth")
                .content(requestString)
                .contentType(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(MockMvcResultMatchers.status().isNotFound())

        where:
        email             | password
        "email@dsm.hs.kr" | "password2"
        "email@dsm.hs.kr" | "password"
    }

//    def "RefreshToken"() {
//    }
}
