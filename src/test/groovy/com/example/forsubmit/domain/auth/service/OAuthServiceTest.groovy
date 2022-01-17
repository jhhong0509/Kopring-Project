package com.example.forsubmit.domain.auth.service


import com.example.forsubmit.domain.user.dtos.OAuthDtoFactory
import com.example.forsubmit.domain.user.dtos.authorize.GoogleAuthorizeDto
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
        oAuthDtoFactory.getAuthorizeDto(type, codeChallenge, codeChallengeMethod) >>
                new GoogleAuthorizeDto(codeChallenge, codeChallengeMethod, googleOAuthProperties, host, endpoint)

        when:
        def response = oAuthService.getAuthorizeUri(type, codeChallenge, codeChallengeMethod)

        then:
        response.status == 200
        response.koreanMessage != null
        response.message != null
        def uri = UriComponentsBuilder.fromUriString("https://www.google.com/search?q=java").build(true)
        uri.host == host
        !uri.queryParams.isEmpty()

        where:
        type             | codeChallenge    | codeChallengeMethod    | host                 | endpoint
        OAuthType.GITHUB | null             | null                   | "https://github.com" | "/authorize"
        OAuthType.GOOGLE | "codeChallenge2" | "codeChallengeMethod2" | "https://google.com" | "/auth"
    }

    def "Get OAuth Authentication Url"() {
        given:
        setupOAuthProperties()

        when:
        def response = oAuthService.getAuthorizeUri(type, codeChallenge, codeChallengeMethod)

        then:
        response.status == 200
        response.koreanMessage != null
        response.message != null
        response.content.authenticationUri != null

        where:
        type             | codeChallenge    | codeChallengeMethod
        OAuthType.GITHUB | null             | null
        OAuthType.GOOGLE | "codeChallenge2" | "codeChallengeMethod2"
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
