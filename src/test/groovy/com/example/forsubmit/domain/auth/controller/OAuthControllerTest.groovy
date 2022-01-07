package com.example.forsubmit.domain.auth.controller

import com.example.forsubmit.BaseControllerTest
import com.example.forsubmit.domain.auth.payload.response.OAuthRedirectUrlResponse
import com.example.forsubmit.domain.auth.service.OAuthService
import com.example.forsubmit.global.payload.BaseResponse
import org.spockframework.spring.SpringBean
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters

@WebMvcTest(OAuthController)
@TestPropertySource("classpath:application.yml")
class OAuthControllerTest extends BaseControllerTest {

    @SpringBean
    private OAuthService oAuthService = GroovyMock(OAuthService)

    def "OAuth Get Auth Url Test"() {
        given:
        def oAuthRedirectUrlResponse = new OAuthRedirectUrlResponse("http://google.com:8181/google")
        oAuthService.getAuthenticationUri(type) >> new BaseResponse(200, "Success", "성공", oAuthRedirectUrlResponse)

        when:
        def result = mockMvc.perform(RestDocumentationRequestBuilders.get("/oauth/auth-endpoint/{type}", type))

        then:
        result.andExpect(MockMvcResultMatchers.status().isOk())
        result.andDo(document("OAuth_Link",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                        parameterWithName("type").description("OAuth 타입")
                ),
                responseFields(
                        fieldWithPath("status").description("Status Code"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지"),
                        fieldWithPath("content.authentication_url").type(JsonFieldType.STRING).description("인증 URL")
                )))

        where:
        type     | _
        "github" | _
        "google" | _
    }
}
