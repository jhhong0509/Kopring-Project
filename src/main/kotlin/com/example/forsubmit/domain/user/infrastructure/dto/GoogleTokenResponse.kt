package com.example.forsubmit.domain.auth.infrastructure.dto

class GoogleTokenResponse(
    val accessToken: String,
    val expiresIn: Long,
    val scope: String,
    val tokenType: String,
    val idToken: String
)