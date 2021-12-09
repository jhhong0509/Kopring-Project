package com.example.forsubmit.domain.post.controller

import com.example.forsubmit.JpaConfig
import com.example.forsubmit.domain.post.payload.request.CreatePostRequest
import com.example.forsubmit.domain.post.service.PostService
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.entity.UserRepository
import com.example.forsubmit.global.security.auth.AuthDetails
import com.example.forsubmit.global.security.exceptions.JwtValidateException
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import com.example.forsubmit.global.security.property.JwtProperties
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@WebMvcTest(PostController)
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "docs.api.com")
@AutoConfigureMockMvc
@MockBean(JpaConfig)
@ActiveProfiles("test")
class PostControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private WebApplicationContext context

    @Autowired
    private ObjectMapper objectMapper

    @MockBean
    private PostService postService

    @SpringBean
    private JwtTokenProvider tokenProvider = GroovyMock(JwtTokenProvider)

    private UserRepository userRepository = GroovyMock(UserRepository)

    @WithMockUser(username = "email@dsm.hs.kr")
    def "Post Controller Layer Test"() {
        given:
        def user = new User(1, "email@dsm.hs.kr", "name", "password", new ArrayList(), new ArrayList(), new ArrayList())
        userRepository.findById(1) >> user
        def req = new CreatePostRequest(title, content)
        def userDetails = new AuthDetails(user)
        tokenProvider.getTokenFromHeader(_) >> "Bearer sadf"
        tokenProvider.authenticateUser(_) >> new UsernamePasswordAuthenticationToken(userDetails, "", new ArrayList())

        when:
        def response = mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .header(JwtProperties.TOKEN_HEADER_NAME, "Bearer asdfasdf")
                .content(objectMapper.writeValueAsString(req)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isCreated())
        response.andDo(document("Save_Post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Authorization").description("Access 토큰")
                ),
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                )))

        where:
        title    | content
        "title"  | "content"
        "title2" | "content2"
    }

    def "Post Controller Layer Fail Test - 401"() {
        given:
        def req = new CreatePostRequest("title", "content")
        tokenProvider.getTokenFromHeader(_) >> "Bearer sadf"
        tokenProvider.authenticateUser(_) >> { throw JwtValidateException.EXCEPTION }

        when:
        def response = mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "asdfsdaf")
                .content(objectMapper.writeValueAsString(req)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isUnauthorized())
        response.andDo(document("Save_Post_401",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Authorization").description("토큰이 이상한 경우")
                ),
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                )))

    }


}
