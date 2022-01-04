package com.example.forsubmit


import com.example.forsubmit.global.exception.ExceptionFilter
import com.example.forsubmit.global.logger.LogFilter
import com.example.forsubmit.global.security.TokenFilter
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.constraints.ConstraintDescriptions
import org.springframework.restdocs.snippet.Attributes
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration

@AutoConfigureRestDocs
class BaseControllerTest extends Specification {

    @Autowired
    protected ObjectMapper objectMapper

    protected MockMvc mockMvc

    @Autowired
    private WebApplicationContext context

    @Autowired
    private RestDocumentationContextProvider provider

    @SpringBean
    protected JwtTokenProvider jwtTokenProvider = GroovyMock(JwtTokenProvider)

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(provider))
                .addFilters(new LogFilter(), new ExceptionFilter(objectMapper), new TokenFilter(jwtTokenProvider))
//                .alwaysDo(print())    // for debug
                .build()
    }

    protected static def getConstraintAttribute(Class<?> requestClazz, String propertyName) {
        ConstraintDescriptions simpleRequestConstraints = new ConstraintDescriptions(requestClazz);
        List<String> nameDescription = simpleRequestConstraints.descriptionsForProperty(propertyName);

        return Attributes.key("constraints").value(nameDescription)
    }
}
