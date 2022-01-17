package com.example.forsubmit.domain.user.properties

sealed interface BaseOAuthProperty {
    val clientId: String
    val clientSecret: String
    val redirectUri: String
    val scope: String
}