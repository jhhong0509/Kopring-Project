//package com.example.forsubmit.domain.chatroom.controller
//
//import com.example.forsubmit.JpaConfig
//import com.example.forsubmit.domain.user.entity.User
//import com.example.forsubmit.global.security.auth.AuthDetails
//import com.example.forsubmit.global.security.jwt.JwtTokenProvider
//import com.example.forsubmit.global.security.property.JwtProperties
//import com.fasterxml.jackson.databind.ObjectMapper
//import org.spockframework.spring.SpringBean
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.http.MediaType
//import org.springframework.restdocs.payload.JsonFieldType
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.test.context.support.WithMockUser
//import org.springframework.test.context.ActiveProfiles
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers
//import spock.lang.Specification
//
//import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
//import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
//import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
//
//@WebMvcTest()
//@AutoConfigureRestDocs(uriScheme = "http", uriHost = "docs.api.com")
//@AutoConfigureMockMvc
//@MockBean(JpaConfig)
//@ActiveProfiles("test")
//class RoomControllerTest extends Specification {
//
//    @Autowired
//    private MockMvc mockMvc
//
//    @Autowired
//    private ObjectMapper objectMapper
//
//    @SpringBean
//    private RoomService roomService = GroovyMock(RoomService)
//
//    @SpringBean
//    private JwtTokenProvider jwtTokenProvider = GroovyMock(JwtTokenProvider)
//
//    @WithMockUser(username = "email@dsm.hs.kr")
//    def "Room Controller Layer Test"() {
//        given:
//        def req = new CreateRoomRequest("title", "content")
//        roomService.savePost(_) >> {}
//        def details = new AuthDetails(new User())
//        jwtTokenProvider.authenticateUser(_) >> new UsernamePasswordAuthenticationToken(details, "", new ArrayList())
//
//        when:
//        def response = mockMvc.perform(post("/room")
//                .contentType(MediaType.APPLICATION_JSON)
//                .header(JwtProperties.TOKEN_HEADER_NAME, "Bearer asdfasdf")
//                .content(objectMapper.writeValueAsString(req)))
//
//        then:
//        response.andExpect(MockMvcResultMatchers.status().isCreated())
//        response.andDo(document("Save_Room",
//                preprocessRequest(prettyPrint()),
//                preprocessResponse(prettyPrint()),
//                requestHeaders(
//                        headerWithName("Authorization").description("Access 토큰")
//                ),
//                requestFields(
//                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
//                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
//                )))
//
//        where:
//        title    | content
//        "title"  | "content"
//        "title2" | "content2"
//    }
//
//    @WithMockUser(username = "email@dsm.hs.kr")
//    def "Room Controller Layer Fail Test - 400"() {
//        given:
//        def req = new CreateRoomRequest("title", "")
//        roomService.savePost(_) >> {}
//        def details = new AuthDetails(new User())
//        jwtTokenProvider.authenticateUser(_) >> new UsernamePasswordAuthenticationToken(details, "", new ArrayList())
//
//        when:
//        def response = mockMvc.perform(post("/room")
//                .contentType(MediaType.APPLICATION_JSON)
//                .header("Authorization", "asdfsdaf")
//                .content(objectMapper.writeValueAsString(req)))
//
//        then:
//        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
//        response.andDo(document("Save_Room_400",
//                preprocessRequest(prettyPrint()),
//                preprocessResponse(prettyPrint()),
//                requestHeaders(
//                        headerWithName("Authorization").description("Access Token")
//                ),
//                requestFields(
//                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
//                        fieldWithPath("content").type(JsonFieldType.STRING).description("잘못된 게시글 내용 (Empty)")
//                )))
//
//    }
//
//
//}
