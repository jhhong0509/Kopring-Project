package com.example.forsubmit.domain.user.service

import com.example.forsubmit.domain.auth.payload.response.TokenResponse
import com.example.forsubmit.domain.user.dtos.OAuthDtoFactory
import com.example.forsubmit.domain.user.dtos.authorize.GithubAuthorizeDto
import com.example.forsubmit.domain.user.dtos.authorize.GoogleAuthorizeDto
import com.example.forsubmit.domain.user.dtos.token.GithubOAuthTokenDto
import com.example.forsubmit.domain.user.dtos.token.GoogleOAuthTokenDto
import com.example.forsubmit.domain.user.entity.OAuthUser
import com.example.forsubmit.domain.user.enums.OAuthType
import com.example.forsubmit.domain.user.facade.UserFacade
import com.example.forsubmit.domain.user.infrastructure.token.GithubTokenClient
import com.example.forsubmit.domain.user.infrastructure.token.GoogleTokenClient
import com.example.forsubmit.domain.user.infrastructure.token.dto.GithubTokenResponse
import com.example.forsubmit.domain.user.infrastructure.token.dto.GoogleTokenResponse
import com.example.forsubmit.domain.user.infrastructure.userinfo.GithubUserInfoClient
import com.example.forsubmit.domain.user.infrastructure.userinfo.GoogleUserInfoClient
import com.example.forsubmit.domain.user.infrastructure.userinfo.dto.GithubUserInfoResponse
import com.example.forsubmit.domain.user.infrastructure.userinfo.dto.GoogleUserInfoResponse
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
    private def googleTokenClient = GroovyMock(GoogleTokenClient)
    private def githubTokenClient = GroovyMock(GithubTokenClient)
    private def googleUserInfoClient = GroovyMock(GoogleUserInfoClient)
    private def githubUserInfoClient = GroovyMock(GithubUserInfoClient)

    def "Get OAuth Authentication Url"() {
        given:
        setupOAuthProperties()
        oAuthDtoFactory.getAuthorizeDto(OAuthType.GOOGLE, _, _) >>
                new GoogleAuthorizeDto("codeChallenge", "S256", googleOAuthProperties, host, endpoint)
        oAuthDtoFactory.getAuthorizeDto(OAuthType.GITHUB, null, null) >>
                new GithubAuthorizeDto(githubOAuthProperties, host, endpoint)

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

    def "OAuth SignIn Or SignUp Test - Github"() {
        given:
        setupOAuthProperties()
        oAuthDtoFactory.getTokenDto(type, code, codeVerifier) >> new GithubOAuthTokenDto(githubOAuthProperties, code, githubTokenClient, githubUserInfoClient, "application/json")
        githubTokenClient.getToken(_) >> new GithubTokenResponse("access_token")
        githubUserInfoClient.getUserInfo(_) >> new GithubUserInfoResponse("id", "name")
        userFacade.saveUser(_) >> new OAuthUser("accountId", "name")
        jwtTokenProvider.getToken(_) >> new TokenResponse("accessToken", "refreshToken")

        when:
        def response = oAuthService.oAuthSignInOrSignUp(type, codeVerifier, code)

        then:
        response.status == 200
        response.koreanMessage != null
        response.message != null
        def uri = UriComponentsBuilder.fromUriString("https://www.google.com/search?q=java").build(true)
        !uri.queryParams.isEmpty()

        where:
        type             | codeVerifier | code
        OAuthType.GITHUB | null         | "github code"
    }

    def "OAuth SignIn Or SignUp Test - Google"() {
        given:
        setupOAuthProperties()
        oAuthDtoFactory.getTokenDto(type, code, codeVerifier) >> new GoogleOAuthTokenDto(googleOAuthProperties, code, "codeVerifier", googleTokenClient, googleUserInfoClient, "application/json")
        googleTokenClient.getToken(_) >> new GoogleTokenResponse("access_token")
        googleUserInfoClient.getUserInfo(_) >> new GoogleUserInfoResponse("id", "name")
        userFacade.saveUser(_) >> new OAuthUser("accountId", "name")
        jwtTokenProvider.getToken(_) >> new TokenResponse("accessToken", "refreshToken")

        when:
        def response = oAuthService.oAuthSignInOrSignUp(type, codeVerifier, code)

        then:
        response.status == 200
        response.koreanMessage != null
        response.message != null
        def uri = UriComponentsBuilder.fromUriString("https://www.google.com/search?q=java").build(true)
        !uri.queryParams.isEmpty()

        where:
        type             | codeVerifier | code
        OAuthType.GOOGLE | "sdfasdf"    | "google code"
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
