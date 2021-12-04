package com.example.forsubmit.domain.auth.entity

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@RedisHash(timeToLive = 60 * 60 * 24 * 14)
class RefreshToken(
    @Id
    val id: Long,

    @Indexed
    @NotBlank
    val token: String
)