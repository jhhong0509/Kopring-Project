package com.example.forsubmit.domain.auth.service

import com.example.forsubmit.domain.auth.exceptions.InvalidOauthTypeException
import com.example.forsubmit.domain.auth.properties.GithubOAuthProperties
import com.example.forsubmit.domain.auth.properties.GoogleOAuthProperties
import spock.lang.Specification

class OAuthServiceTest extends Specification {

    private GoogleOAuthProperties googleOAuthProperties = GroovyMock(GoogleOAuthProperties)
    private GithubOAuthProperties githubOAuthProperties = GroovyMock(GithubOAuthProperties)
    private def oAuthService = new OAuthService(githubOAuthProperties, googleOAuthProperties)

    def "Get OAuth Authentication Url"() {
        given:
        setupOAuthProperties()

        when:
        def response = oAuthService.getAuthorizeUri(type, codeChallenge, codeChallengeMethod)

        then:
        response.status == 200
        response.koreanMessage != null
        response.message != null
        response.content.authenticationUrl != null

        where:
        type     | codeChallenge    | codeChallengeMethod
        "github" | null             | null
        "google" | "codeChallenge2" | "codeChallengeMethod2"
    }

    def "Get OAuth Authentication Url - Invalid Type"() {
        when:
        oAuthService.getAuthorizeUri("Invalid Type", null, null)

        then:
        thrown(InvalidOauthTypeException)
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
