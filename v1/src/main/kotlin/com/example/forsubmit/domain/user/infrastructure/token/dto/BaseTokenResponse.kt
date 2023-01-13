package com.example.forsubmit.domain.user.infrastructure.token.dto

interface BaseTokenResponse {
    fun getToken(): String
}