package com.example.forsubmit.domain.user.infrastructure.token.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class GoogleTokenResponse(
    @JsonProperty("access_token")
    private val accessToken: String
): BaseTokenResponse {
    override fun getToken() = accessToken
}