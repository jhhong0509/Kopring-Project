package com.example.forsubmit.domain.post.controller

import com.example.forsubmit.BaseTest
import com.example.forsubmit.domain.post.payload.request.CreatePostRequest
import com.example.forsubmit.domain.post.payload.request.UpdatePostRequest
import com.example.forsubmit.domain.post.service.PostService
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.global.security.auth.AuthDetails
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import com.example.forsubmit.global.security.property.JwtProperties
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.context.WebApplicationContext

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.payload.PayloadDocumentation.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@WebMvcTest(PostController)
@ActiveProfiles("test")
class PostControllerTest extends BaseTest {

    @Autowired
    private WebApplicationContext context

    @Autowired
    private ObjectMapper objectMapper

    @SpringBean
    private PostService postService = GroovyMock(PostService)

    @SpringBean
    private JwtTokenProvider jwtTokenProvider = GroovyMock(JwtTokenProvider)

    @WithMockUser(username = "email@dsm.hs.kr")
    def "Post Controller Save Test"() {
        given:
        def req = new CreatePostRequest("title", "content")
        postService.savePost(_) >> {}
        def details = new AuthDetails(new User())
        jwtTokenProvider.authenticateUser(_) >> new UsernamePasswordAuthenticationToken(details, "", new ArrayList())

        when:
        def response = mockMvc
                .perform(post("/post")
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

    @WithMockUser(username = "email@dsm.hs.kr")
    def "Post Controller Save Fail Test - 400"() {
        given:
        def req = new CreatePostRequest("title", "")
        postService.savePost(_) >> {}
        def details = new AuthDetails(new User())
        jwtTokenProvider.authenticateUser(_) >> new UsernamePasswordAuthenticationToken(details, "", new ArrayList())

        when:
        def response = mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "asdfsdaf")
                .content(objectMapper.writeValueAsString(req)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
        response.andDo(document("Save_Post_400",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                ),
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("잘못된 게시글 내용 (Empty)")
                ),
                responseFields(
                        fieldWithPath("status").description("Status Code"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지"),
                        fieldWithPath("content").description("빈 content")
                )))
    }

    @WithMockUser(username = "email@dsm.hs.kr")
    def "Post Controller Update Fail Test - 400"() {
        given:
        def req = new UpdatePostRequest("title", "")
        postService.updatePost(_) >> {}
        def details = new AuthDetails(new User())
        jwtTokenProvider.authenticateUser(_) >> new UsernamePasswordAuthenticationToken(details, "", new ArrayList())

        when:
        def response = mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "asdfsdaf")
                .content(objectMapper.writeValueAsString(req)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
        response.andDo(document("Save_Post_400",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                ),
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("잘못된 게시글 내용 (Empty)")
                )))
    }

}
