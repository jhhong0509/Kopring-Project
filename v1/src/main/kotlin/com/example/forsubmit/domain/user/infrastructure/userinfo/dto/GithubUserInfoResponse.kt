package com.example.forsubmit.domain.user.infrastructure.userinfo.dto

import com.fasterxml.jackson.annotation.JsonProperty

class GithubUserInfoResponse(
    @JsonProperty("name")
    private val userName: String,
    @JsonProperty("login")
    private val userId: String
): BaseUserInfoResponse {
    override fun getAccountId(): String = userId

    override fun getName(): String = userName
}