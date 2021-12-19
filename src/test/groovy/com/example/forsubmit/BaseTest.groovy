package com.example.forsubmit

import com.example.forsubmit.global.exception.ExceptionFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@AutoConfigureRestDocs
class BaseTest extends Specification {

    protected MockMvc mockMvc

    @Autowired
    private WebApplicationContext context

    @Autowired
    private RestDocumentationContextProvider provider

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(provider))
                .addFilters(new ExceptionFilter())
                .alwaysDo(print())
                .build()
    }
}
