package com.example.forsubmit.domain.user.controller

import com.example.forsubmit.BaseTest
import com.example.forsubmit.TestUtils
import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.user.exceptions.EmailAlreadyExistsException
import com.example.forsubmit.domain.user.payload.request.SignUpRequest
import com.example.forsubmit.domain.user.service.UserService
import com.example.forsubmit.global.payload.BaseResponse
import org.spockframework.spring.SpringBean
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.payload.PayloadDocumentation.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@WebMvcTest(UserController)
class UserControllerTest extends BaseTest {

    @SpringBean
    private UserService userService = GroovyMock(UserService)

    def "Sign Up Success"() {
        given:
        def request = new SignUpRequest()
        TestUtils.setVariable("email", email, request)
        TestUtils.setVariable("password", password, request)
        TestUtils.setVariable("name", name, request)
        userService.saveUser(_) >> new BaseResponse(201, "Sign Up Success", "회원가입에 성공했습니다", new TokenResponse("accessToken", "refreshToken"))

        when:
        def response = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isCreated())
        response.andDo(document("Sign_Up",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일")
                                .attributes(getConstraintAttribute(SignUpRequest, "email")),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                .attributes(getConstraintAttribute(SignUpRequest, "password")),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
                                .attributes(getConstraintAttribute(SignUpRequest, "name"))
                ),
                responseFields(
                        fieldWithPath("status").description("상태코드"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지"),
                        fieldWithPath("content.access_token").description("AccessToken"),
                        fieldWithPath("content.refresh_token").description("RefreshToken")
                )))

        where:
        email             | name   | password
        "email@dsm.hs.kr" | "name" | "password"
        "email@dsm.hs.kr" | "1"    | "password22"
    }

    def "Sign Up Failed - 400"() {
        given:
        def request = new SignUpRequest()
        TestUtils.setVariable("email", email, request)
        TestUtils.setVariable("password", password, request)
        TestUtils.setVariable("name", name, request)

        when:
        def response = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        then:
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
        response.andDo(document("Sign_Up_400",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일")
                                .attributes(getConstraintAttribute(SignUpRequest, "email")),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                .attributes(getConstraintAttribute(SignUpRequest, "password")),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
                                .attributes(getConstraintAttribute(SignUpRequest, "name"))
                ),
                responseFields(
                        fieldWithPath("status").description("상태코드"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지")
                )))

        where:
        email             | name      | password
        "invalid Email"   | "name"    | "password"
        "valid@dms.hs.kr" | ""        | "password22"
        "valid@dms.hs.kr" | "afsadsf" | "jknk"
    }

    def "User Already Exist"() {
        given:
        def request = new SignUpRequest()
        TestUtils.setVariable("email", email, request)
        TestUtils.setVariable("password", password, request)
        TestUtils.setVariable("name", name, request)
        userService.saveUser(_) >> { throw EmailAlreadyExistsException.EXCEPTION }

        when:
        def response = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())

        then:
        response.andExpect(MockMvcResultMatchers.status().isConflict())
        response.andDo(document("Sign_Up_409",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일")
                                .attributes(getConstraintAttribute(SignUpRequest, "email")),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                                .attributes(getConstraintAttribute(SignUpRequest, "password")),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
                                .attributes(getConstraintAttribute(SignUpRequest, "name"))
                ),
                responseFields(
                        fieldWithPath("status").description("상태코드"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지")
                )))

        where:
        email             | name   | password
        "email@dsm.hs.kr" | "name" | "password"
        "email@hsad.sda"  | "1"    | "password22"
    }
}
