package com.example.forsubmit.domain.user.dtos.authorize

sealed interface OAuthPKCEAuthorizeDto {
    val codeChallenge: String
    val codeChallengeMethod: String
}