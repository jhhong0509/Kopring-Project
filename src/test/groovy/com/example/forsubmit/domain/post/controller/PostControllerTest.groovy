package com.example.forsubmit.domain.post.controller

import com.example.forsubmit.BaseTest
import com.example.forsubmit.domain.post.exceptions.CannotUpdatePostException
import com.example.forsubmit.domain.post.payload.request.CreatePostRequest
import com.example.forsubmit.domain.post.payload.request.UpdatePostRequest
import com.example.forsubmit.domain.post.payload.response.SavePostResponse
import com.example.forsubmit.domain.post.service.PostService
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.global.payload.BaseResponse
import com.example.forsubmit.global.security.auth.AuthDetails
import com.example.forsubmit.global.security.property.JwtProperties
import org.spockframework.spring.SpringBean
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.payload.PayloadDocumentation.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@WebMvcTest(PostController)
@ActiveProfiles("test")
class PostControllerTest extends BaseTest {

    @SpringBean
    private PostService postService = GroovyMock(PostService)

    @WithMockUser(username = "email@dsm.hs.kr")
    def "Post Controller Save Test"() {
        given:
        def req = new CreatePostRequest("title", "content")
        postService.savePost(_) >> new BaseResponse(201, "Save Success", "저장 성공", new SavePostResponse(1))
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
                ),
                responseFields(
                        fieldWithPath("status").description("Status Code"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지"),
                        fieldWithPath("content.id").description("저장된 게시글 id")
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
                        fieldWithPath("korean_message").description("한글 응답 메세지")
                )))
    }

    def "Post Controller Update Success Test"() {
        given:
        def req = new UpdatePostRequest("title", "content")
        postService.updatePost(_, _) >> { new BaseResponse(200, "Update Success", "수정 성공", null) }
        def details = new AuthDetails(new User())
        jwtTokenProvider.authenticateUser(_) >> new UsernamePasswordAuthenticationToken(details, "", new ArrayList())

        when:
        def response = mockMvc.perform(patch("/post/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "asdfsdaf")
                .content(objectMapper.writeValueAsString(req)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andDo(document("Update_Post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                ),
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 내용")
                ),
                responseFields(
                        fieldWithPath("status").description("상태코드"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지")
                )))
    }

    def "Post Controller Update Fail Test - 403"() {
        given:
        def req = new UpdatePostRequest("title", "content")
        postService.updatePost(_, _) >> { throw CannotUpdatePostException.EXCEPTION }
        def details = new AuthDetails(new User())
        jwtTokenProvider.authenticateUser(_) >> new UsernamePasswordAuthenticationToken(details, "", new ArrayList())

        when:
        def response = mockMvc.perform(patch("/post/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "asdfsdaf")
                .content(objectMapper.writeValueAsString(req)))
                .andDo(print())

        then:
        response.andExpect(MockMvcResultMatchers.status().isForbidden())
        response.andDo(document("Update_Post_403",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                ),
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 내용")
                ),
                responseFields(
                        fieldWithPath("status").description("상태코드"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지")
                )))
    }

}
