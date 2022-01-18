package com.example.forsubmit.domain.user.dtos

import com.example.forsubmit.domain.user.enums.OAuthType
import com.example.forsubmit.domain.user.infrastructure.token.GithubTokenClient
import com.example.forsubmit.domain.user.infrastructure.token.GoogleTokenClient
import com.example.forsubmit.domain.user.infrastructure.userinfo.GithubUserInfoClient
import com.example.forsubmit.domain.user.infrastructure.userinfo.GoogleUserInfoClient
import com.example.forsubmit.domain.user.properties.GithubOAuthProperties
import com.example.forsubmit.domain.user.properties.GoogleOAuthProperties
import spock.lang.Specification

class OAuthDtoFactoryTest extends Specification {
    private def googleOAuthProperties = GroovyMock(GoogleOAuthProperties)
    private def githubOAuthProperties = GroovyMock(GithubOAuthProperties)
    private def githubTokenClient = GroovyMock(GithubTokenClient)
    private def googleTokenClient = GroovyMock(GoogleTokenClient)
    private def githubUserInfoClient = GroovyMock(GithubUserInfoClient)
    private def googleUserInfoClient = GroovyMock(GoogleUserInfoClient)

    private def oAuthDtoFactory = new OAuthDtoFactory(googleOAuthProperties, githubOAuthProperties, googleTokenClient, githubTokenClient, googleUserInfoClient, githubUserInfoClient)

    def "getTokenDto"() {
        when:
        def dto = oAuthDtoFactory.getTokenDto(type, code, codeVerifier)

        then:
        dto.accept != null
        dto.baseOAuthProperty != null
        dto.code == code
        dto.OAuthTokenClient != null
        dto.OAuthUserInfoClient != null

        where:
        type             | code   | codeVerifier
        OAuthType.GITHUB | "code" | null
        OAuthType.GOOGLE | "code" | "codeVerifier"
    }

    def "getAuthorizeDto"() {
        when:
        def dto = oAuthDtoFactory.getAuthorizeDto(type, codeChallenge, codeChallengeMethod)

        then:
        dto.baseOAuthProperty != null
        dto.endpoint != null
        dto.url != null

        where:
        type             | codeChallenge | codeChallengeMethod
        OAuthType.GITHUB | null          | null
        OAuthType.GOOGLE | "code"        | "S256"
    }

}
