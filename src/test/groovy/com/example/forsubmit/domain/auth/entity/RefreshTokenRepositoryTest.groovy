package com.example.forsubmit.domain.auth.entity

import com.example.forsubmit.global.redis.EmbeddedRedisConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@DataRedisTest
@ActiveProfiles("test")
@Import(EmbeddedRedisConfig)
class RefreshTokenRepositoryTest extends Specification {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository

    def "Save Token Success Test"() {
        given:
        def refreshToken = new RefreshToken(accoutId, token, exp)

        when:
        refreshTokenRepository.save(refreshToken)

        then:
        noExceptionThrown()

        where:
        accoutId    | token        | exp
        "accoutId1" | "token1212"  | 10100000
        "accoutId2" | "token31231" | 0
    }

    def "find token Success Test"() {
        given:
        def refreshToken = new RefreshToken(accountId, token, exp)
        refreshTokenRepository.save(refreshToken)

        when:
        def refresh = refreshTokenRepository.findByToken(token)

        then:
        refresh.token == token
        refresh.accountId == accountId
        refresh.ttl == exp

        where:
        accountId   | token        | exp
        "accoutId1" | "token1212"  | 10100000
        "accoutId2" | "token31231" | 0
    }

}
