package com.example.forsubmit.domain.auth.controller

import com.example.forsubmit.BaseControllerTest
import com.example.forsubmit.domain.user.controller.OAuthController
import com.example.forsubmit.domain.user.enums.OAuthType
import com.example.forsubmit.domain.user.payload.response.OAuthRedirectUriResponse
import com.example.forsubmit.domain.user.service.OAuthService
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
import static org.springframework.restdocs.request.RequestDocumentation.*

@WebMvcTest(OAuthController)
@TestPropertySource("classpath:application.yml")
class OAuthControllerTest extends BaseControllerTest {

    @SpringBean
    private OAuthService oAuthService = GroovyMock(OAuthService)

    def "OAuth Get Auth Url With PKCE Test"() {
        given:
        def oAuthRedirectUriResponse = new OAuthRedirectUriResponse("https://github.com:8181/google")
        oAuthService.getAuthorizeUri(type, codeChallenge, codeChallengeMethod) >> new BaseResponse(200, "Success", "성공", oAuthRedirectUriResponse)

        when:
        def result = mockMvc.perform(RestDocumentationRequestBuilders.get("/oauth/{type}/authorize-uri", type)
                .queryParam("codeChallenge", codeChallenge)
                .queryParam("codeChallengeMethod", codeChallengeMethod))

        then:
        result.andExpect(MockMvcResultMatchers.status().isOk())
        result.andDo(document("OAuth_Link_PKCE",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                        parameterWithName("type").description("OAuth 타입")
                ),
                requestParameters(
                        parameterWithName("codeChallenge").description("code_verifier값을 암호화 한 후, Base64URLEncoding 한 값").optional(),
                        parameterWithName("codeChallengeMethod").description("codeChallenge를 암호화 한 방식").optional()
                ),
                responseFields(
                        fieldWithPath("status").description("Status Code"),
                        fieldWithPath("message").description("응답 메세지"),
                        fieldWithPath("korean_message").description("한글 응답 메세지"),
                        fieldWithPath("content.authentication_url").type(JsonFieldType.STRING).description("인증 URL")
                )))

        where:
        type             | codeChallenge | codeChallengeMethod
        OAuthType.GOOGLE | "asdfsadf"    | "S256"
    }

    def "OAuth Get Auth Url Test"() {
        given:
        def oAuthRedirectUriResponse = new OAuthRedirectUriResponse("http://github.com:8181/github")
        oAuthService.getAuthorizeUri(type, null, null) >> new BaseResponse(200, "Success", "성공", oAuthRedirectUriResponse)

        when:
        def result = mockMvc.perform(RestDocumentationRequestBuilders.get("/oauth/{type}/authorize-uri", type))

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
        type             | _
        OAuthType.GITHUB | _
    }
}
