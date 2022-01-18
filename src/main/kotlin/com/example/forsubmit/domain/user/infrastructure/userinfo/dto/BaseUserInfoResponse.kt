package com.example.forsubmit.domain.user.infrastructure.userinfo.dto

interface BaseUserInfoResponse {
    fun getAccountId(): String
    fun getName(): String
}