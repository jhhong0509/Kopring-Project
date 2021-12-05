package com.example.forsubmit.domain.auth.entity

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@RedisHash
class RefreshToken(
    @Id
    val id: Long,

    @Indexed
    @NotBlank
    val token: String,

    @TimeToLive
    var ttl: Long
)