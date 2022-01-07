package com.example.forsubmit.domain.auth.service

import com.example.forsubmit.domain.auth.exceptions.InvalidOauthTypeException
import com.example.forsubmit.domain.auth.properties.OAuthProperties
import spock.lang.Specification

class OAuthServiceTest extends Specification {

    private OAuthProperties oAuthProperties = GroovyMock(OAuthProperties)
    private def oAuthService = new OAuthService(oAuthProperties)

    def "Get OAuth Authentication Url"() {
        given:
        setupOAuthProperties()

        when:
        def response = oAuthService.getAuthenticationUri(type)

        then:
        response.status == 200
        response.koreanMessage != null
        response.message != null
        response.content.authenticationUrl != null

        where:
        type     | _
        "github" | _
        "google" | _
    }

    def "Get OAuth Authentication Url - Invalid Type"() {
        when:
        oAuthService.getAuthenticationUri("Invalid Type")

        then:
        thrown(InvalidOauthTypeException)
    }

    private def setupOAuthProperties() {
        oAuthProperties.google >> new OAuthProperties.Google("yohoho", "yohohohoho", "localhost/github/redirect", "email")
        oAuthProperties.github >> new OAuthProperties.Github("yohow", "yoyooyo", "localhost/github/redirect", "email")
    }

}
