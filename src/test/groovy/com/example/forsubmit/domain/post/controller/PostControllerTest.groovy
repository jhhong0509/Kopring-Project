package com.example.forsubmit.domain.post.controller

import com.example.forsubmit.BaseTest
import com.example.forsubmit.TestUtils
import com.example.forsubmit.domain.post.exceptions.CannotDeletePostException
import com.example.forsubmit.domain.post.exceptions.CannotUpdatePostException
import com.example.forsubmit.domain.post.exceptions.PostNotFoundException
import com.example.forsubmit.domain.post.payload.request.CreatePostRequest
import com.example.forsubmit.domain.post.payload.response.*
import com.example.forsubmit.domain.post.service.PostService
import com.example.forsubmit.domain.user.entity.User
import com.example.forsubmit.domain.user.payload.request.SignUpRequest
import com.example.forsubmit.global.exception.CustomExceptionHandler
import com.example.forsubmit.global.payload.BaseResponse
import com.example.forsubmit.global.security.auth.AuthDetails
import com.example.forsubmit.global.security.property.JwtProperties
import org.spockframework.spring.SpringBean
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

import java.time.LocalDateTime

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.payload.PayloadDocumentation.*
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@WebMvcTest([PostController])
@Import(CustomExceptionHandler)
@ActiveProfiles("test")
class PostControllerTest extends BaseTest {

    @SpringBean
    private PostService postService = GroovyMock(PostService)

    def setup() {
        def details = new AuthDetails(new User())
        jwtTokenProvider.authenticateUser(_) >> new UsernamePasswordAuthenticationToken(details, "", new ArrayList())
        jwtTokenProvider.getTokenFromHeader(_) >> "sdafadsf"
    }

    @WithMockUser(username = "email@dsm.hs.kr")
    def "Post Controller Save Test"() {
        given:
        def request = new CreatePostRequest()
        TestUtils.setVariable("title", "title", request)
        TestUtils.setVariable("content", "content", request)

        postService.savePost(_) >> new BaseResponse(201, "Save Success", "저장 성공", new SavePostResponse(1))

        when:
        def response = mockMvc
                .perform(post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtProperties.TOKEN_HEADER_NAME, token)
                        .content(objectMapper.writeValueAsString(request)))

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
        title    | content    | token
        "title"  | "content"  | "Bearer dsafasdf"
        "title2" | "content2" | "Bearer asdfadsf"
    }

    @WithMockUser(username = "email@dsm.hs.kr")
    def "Post Controller Save Fail Test - 400"() {
        given:
        def request = new CreatePostRequest()
        TestUtils.setVariable("title", "title", request)
        TestUtils.setVariable("content", "", request)

        when:
        def response = mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "asdfsdaf")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

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

    def "Post Controller Update Test"() {
        given:
        def token = "Bearer dsafadsf"
        def request = new CreatePostRequest()
        TestUtils.setVariable("title", "title", request)
        TestUtils.setVariable("content", "content", request)

        postService.updatePost(_, _) >> { new BaseResponse(200, "Update Success", "수정 성공", null) }

        when:
        def response = mockMvc.perform(RestDocumentationRequestBuilders.patch("/post/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "asdfsdaf")
                .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andDo(document("Update_Post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                ),
                pathParameters(
                        parameterWithName("id").description("삭제할 id")
                ),
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 제목")
                                .attributes(getConstraintAttribute(SignUpRequest, "title")),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 내용")
                                .attributes(getConstraintAttribute(SignUpRequest, "content"))
                ),
                responseFields(
                        fieldWithPath("status").description("상태코드"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지")
                )))

    }

    def "Post Controller Update Fail Test - 403"() {
        given:
        def request = new CreatePostRequest()
        TestUtils.setVariable("title", "title", request)
        TestUtils.setVariable("content", "content", request)

        postService.updatePost(_, _) >> { throw CannotUpdatePostException.EXCEPTION }

        when:
        def response = mockMvc.perform(RestDocumentationRequestBuilders.patch("/post/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "asdfsdaf")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        response.andExpect(MockMvcResultMatchers.status().isForbidden())
        response.andDo(document("Update_Post_403",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                ),
                pathParameters(
                        parameterWithName("id").description("삭제할 id")
                ),
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 제목")
                                .attributes(getConstraintAttribute(SignUpRequest, "title")),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 내용")
                                .attributes(getConstraintAttribute(SignUpRequest, "content"))
                ),
                responseFields(
                        fieldWithPath("status").description("상태코드"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지")
                )))
    }

    def "Post Controller Delete Test"() {
        given:
        postService.deletePost(_) >> { new BaseResponse(200, "Delete Success", "삭제 성공", new DeletePostResponse(1)) }

        when:
        def response = mockMvc.perform(RestDocumentationRequestBuilders.delete("/post/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "asdfsdaf"))
                .andDo(print())

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andDo(document("Delete_Post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                ),
                pathParameters(
                        parameterWithName("id").description("삭제할 id")
                ),
                responseFields(
                        fieldWithPath("status").description("상태코드"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지"),
                        fieldWithPath("content.id").description("삭제된 게시글 id")
                )))
    }

    def "Post Controller Delete Fail Test - 403"() {
        given:
        postService.deletePost(_) >> { throw CannotDeletePostException.EXCEPTION }

        when:
        def response = mockMvc.perform(RestDocumentationRequestBuilders.delete("/post/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "asdfsdaf"))
                .andDo(print())

        then:
        response.andExpect(MockMvcResultMatchers.status().isForbidden())
        response.andDo(document("Delete_Post_403",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                ),
                pathParameters(
                        parameterWithName("id").description("삭제할 id")
                ),
                responseFields(
                        fieldWithPath("status").description("상태코드"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지")
                )))
    }

    def "Post Controller Get Single Post"() {
        given:
        def postResponse = new PostContentResponse(title, content, createDate, name, email)
        def serviceResponse = new BaseResponse(200, "Get Post Success", "단건 조회 성공", postResponse)
        postService.getSinglePost(_) >> { serviceResponse }

        when:
        def response = mockMvc.perform(RestDocumentationRequestBuilders.get("/post/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "asdfsdaf"))
                .andDo(print())

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andDo(document("Get_Single_Post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                ),
                pathParameters(
                        parameterWithName("id").description("조회할 게시물 id")
                ),
                responseFields(
                        fieldWithPath("status").description("상태코드"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지"),
                        fieldWithPath("content.title").description("제목"),
                        fieldWithPath("content.content").description("내용"),
                        fieldWithPath("content.created_at").description("게시글 작성일"),
                        fieldWithPath("content.user_name").description("작성자 이름"),
                        fieldWithPath("content.user_email").description("작성자 이메일")
                )))

        where:
        title   | content   | email              | name   | createDate
        " "     | "  "      | "test22@dsm.hs.kr" | "  "   | LocalDateTime.of(2021, 5, 3, 10, 5, 2)
        "title" | "content" | "test@dsm.hs.kr"   | "name" | LocalDateTime.now()
    }

    def "Post Controller Get Single Post - Not Found"() {
        given:
        postService.getSinglePost(_) >> { throw PostNotFoundException.EXCEPTION }

        when:
        def response = mockMvc.perform(RestDocumentationRequestBuilders.get("/post/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "asdfsdaf"))
                .andDo(print())

        then:
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
        response.andDo(document("Get_Single_Post_404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                ),
                pathParameters(
                        parameterWithName("id").description("없는 게시물 id")
                ),
                responseFields(
                        fieldWithPath("status").description("상태코드"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지")
                )))
    }

    def "Post Controller Get Post List"() {
        given:
        def user = new User(email, name, "password")
        def post = new PostResponse(1, title, createDate, user.name, user.email)
        def postListResponse = new PostListResponse(List.of(post, post, post, post), 10)
        postService.getPostList(_) >> new BaseResponse(200, "Get Post List Success", "게시글 조회 성공", postListResponse)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/post")
                .queryParam("lastId", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "asdfsdaf"))
                .andDo(print())

        then:
        response.andExpect(MockMvcResultMatchers.status().isOk())
        response.andDo(document("Get_Post_List",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                ),
                responseFields(
                        fieldWithPath("status").description("상태코드"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지"),
                        fieldWithPath("content.next_id").description("다음 게시글의 id"),
                        fieldWithPath("content.responses[].id").description("게시글 id"),
                        fieldWithPath("content.responses[].title").description("게시글 제목"),
                        fieldWithPath("content.responses[].created_at").description("작성일"),
                        fieldWithPath("content.responses[].user_name").description("작성자 이름"),
                        fieldWithPath("content.responses[].user_email").description("작성자 이메일"),
                )))

        where:
        title   | content   | email              | name   | createDate
        " "     | "  "      | "test22@dsm.hs.kr" | "  "   | LocalDateTime.of(2021, 5, 3, 10, 5, 2)
        "title" | "content" | "test@dsm.hs.kr"   | "name" | LocalDateTime.now()
    }

}
