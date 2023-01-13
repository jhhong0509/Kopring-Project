package com.example.forsubmit.domain.user.infrastructure.userinfo.dto

import com.fasterxml.jackson.annotation.JsonProperty

class GoogleUserInfoResponse(
    @JsonProperty("name")
    private val userName: String,
    @JsonProperty("email")
    private val userEmail: String
): BaseUserInfoResponse {
    override fun getAccountId(): String = userEmail

    override fun getName(): String = userName
}