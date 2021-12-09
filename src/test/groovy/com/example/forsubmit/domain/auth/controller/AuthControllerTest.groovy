package com.example.forsubmit.domain.auth.controller


import com.example.forsubmit.domain.auth.entity.RefreshTokenRepository
import com.example.forsubmit.domain.auth.exceptions.RefreshTokenNotFoundException
import com.example.forsubmit.domain.auth.payload.request.AuthRequest
import com.example.forsubmit.domain.auth.payload.response.AccessTokenResponse
import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.auth.service.AuthService
import com.example.forsubmit.domain.user.exceptions.UserNotFoundException
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.payload.PayloadDocumentation.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

@WebMvcTest(AuthController)
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "docs.api.com")
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    private RefreshTokenRepository refreshTokenRepository = GroovyMock(RefreshTokenRepository)

    @SpringBean
    private JwtTokenProvider jwtTokenProvider = GroovyMock(JwtTokenProvider)

    @SpringBean
    private AuthService authService = GroovyMock(AuthService)

    def "Sign In Success"() {
        given:
        def request = new AuthRequest(email, requestPassword)
        def requestString = objectMapper.writeValueAsString(request)

        authService.signIn(_) >> { new TokenResponse("test", "test") }

        when:
        def result = mockMvc.perform(post("/auth")
                .content(requestString)
                .contentType(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(MockMvcResultMatchers.status().isCreated())
        result.andDo(document("Sign_In",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                ),
                responseFields(
                        fieldWithPath("access_token").type(JsonFieldType.STRING).description("Access Token"),
                        fieldWithPath("refresh_token").type(JsonFieldType.STRING).description("Refresh Token")
                )))

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

        authService.signIn(_) >> { throw UserNotFoundException.EXCEPTION }

        when:
        def result = mockMvc.perform(post("/auth")
                .content(requestString)
                .contentType(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(MockMvcResultMatchers.status().isNotFound())
        result.andDo(document("Sign_In_404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("잘못된 비밀번호")
                )))

        where:
        email             | password
        "email@dsm.hs.kr" | "password2"
        "email@dsm.hs.kr" | "password"
    }

    def "Token Refresh Success"() {
        given:
        authService.tokenRefresh(_) >> new AccessTokenResponse("test")

        when:
        def result = mockMvc.perform(put("/auth")
                .header("Refresh-Token", refreshToken))
                .andDo(document("Token_Refresh",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Refresh-Token").description("Refresh Token")
                        ),
                        responseFields(
                                fieldWithPath("access_token").description("Access Token")
                        )
                ))

        then:
        result.andExpect(MockMvcResultMatchers.status().isOk())

        def response = result.andReturn()
                .response.contentAsString

        def accessToken = objectMapper.readValue(response, AccessTokenResponse)

        accessToken.accessToken != null

        where:
        refreshToken | userEmail
        "asdf"       | "sadfasdf"
        "asdfasdf"   | "sadfasdf"
    }

    def "Token Refresh Not Found"() {
        when:
        authService.tokenRefresh(_) >> { throw RefreshTokenNotFoundException.EXCEPTION }
        def result = mockMvc.perform(put("/auth")
                .header("Refresh-Token", refreshToken))

        then:
        result.andExpect(MockMvcResultMatchers.status().isNotFound())
        result.andDo(document("Token_Refresh_404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Refresh-Token").description("Refresh Token")
                )))

        where:
        refreshToken | _
        "asdf"       | _
        "asdfasdf"   | _
    }

}
