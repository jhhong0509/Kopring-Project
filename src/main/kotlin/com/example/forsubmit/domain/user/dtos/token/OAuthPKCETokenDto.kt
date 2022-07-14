package com.example.forsubmit.domain.user.dtos.token

sealed interface OAuthPKCETokenDto {
    val codeVerifier: String
}