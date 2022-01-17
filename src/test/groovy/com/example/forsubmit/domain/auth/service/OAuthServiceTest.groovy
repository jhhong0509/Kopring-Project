package com.example.forsubmit.domain.auth.service

import com.example.forsubmit.domain.user.dtos.OAuthDtoFactory
import com.example.forsubmit.domain.user.dtos.authorize.GithubAuthorizeDto
import com.example.forsubmit.domain.user.enums.OAuthType
import com.example.forsubmit.domain.user.facade.UserFacade
import com.example.forsubmit.domain.user.properties.GithubOAuthProperties
import com.example.forsubmit.domain.user.properties.GoogleOAuthProperties
import com.example.forsubmit.domain.user.service.OAuthService
import com.example.forsubmit.global.security.jwt.JwtTokenProvider
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

class OAuthServiceTest extends Specification {

    private def userFacade = GroovyMock(UserFacade)
    private def oAuthDtoFactory = GroovyMock(OAuthDtoFactory)
    private def jwtTokenProvider = GroovyMock(JwtTokenProvider)
    private def oAuthService = new OAuthService(userFacade, oAuthDtoFactory, jwtTokenProvider)
    private def googleOAuthProperties = GroovyMock(GoogleOAuthProperties)
    private def githubOAuthProperties = GroovyMock(GithubOAuthProperties)

    def "Get OAuth Authentication Url"() {
        given:
        setupOAuthProperties()
        oAuthDtoFactory.getAuthorizeDto(type, null, null) >>
                new GithubAuthorizeDto(googleOAuthProperties, host, endpoint)

        when:
        def response = oAuthService.getAuthorizeUri(type, null, null)

        then:
        response.status == 200
        response.koreanMessage != null
        response.message != null
        def uri = UriComponentsBuilder.fromUriString("https://www.google.com/search?q=java").build(true)
        !uri.queryParams.isEmpty()

        where:
        type             | host                 | endpoint
        OAuthType.GITHUB | "https://github.com" | "/authorize"
        OAuthType.GOOGLE | "https://google.com" | "/auth"
    }


    private def setupOAuthProperties() {
        googleOAuthProperties.clientId >> "yohoho"
        googleOAuthProperties.clientSecret >> "yohohohoho"
        googleOAuthProperties.redirectUri >> "localhost/github/redirect"
        googleOAuthProperties.scope >> "email"

        githubOAuthProperties.clientId >> "yohow"
        githubOAuthProperties.clientSecret >> "yoyooyo"
        githubOAuthProperties.redirectUri >> "localhost/github/redirect"
        githubOAuthProperties.scope >> "email"
    }

}
