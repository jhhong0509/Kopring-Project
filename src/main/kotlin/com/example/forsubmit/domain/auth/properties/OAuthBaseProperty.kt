package com.example.forsubmit.domain.auth.properties

interface OAuthBaseProperty {
    val clientId: String
    val clientSecret: String
    val redirectUrl: String
    val scope: String
    val baseUrl: String
    val authorizeUri: String
}