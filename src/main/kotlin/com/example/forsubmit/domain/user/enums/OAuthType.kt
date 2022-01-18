package com.example.forsubmit.domain.user.enums

enum class OAuthType(
    val tokenPrefix: String
) {
    GOOGLE("Bearer"),
    GITHUB("token")

}