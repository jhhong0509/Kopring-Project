package com.example.forsubmit.domain.auth.entity

import com.example.forsubmit.global.redis.EmbeddedRedisConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@DataRedisTest
@ActiveProfiles("test")
@Import (EmbeddedRedisConfig)
class RefreshTokenRepositoryTest extends Specification {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    def "Save Token Success Test"() {
        given:
        def refreshToken = new RefreshToken(email, token, exp)

        when:
        refreshTokenRepository.save(refreshToken)

        then:
        noExceptionThrown()

        where:
        email    | token        | exp
        "email1" | "token1212"  | 10100000
        "email2" | "token31231" | 0
    }

    def "find token Success Test"() {
        given:
        def refreshToken = new RefreshToken(email, token, exp)
        refreshTokenRepository.save(refreshToken)

        when:
        def refresh = refreshTokenRepository.findByToken(token)

        then:
        refresh.token == token
        refresh.email == email

        where:
        email    | token        | exp
        "email1" | "token1212"  | 10100000
        "email2" | "token31231" | 0
    }

}
