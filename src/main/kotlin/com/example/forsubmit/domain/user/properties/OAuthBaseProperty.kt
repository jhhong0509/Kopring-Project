package com.example.forsubmit.domain.auth.properties

interface OAuthBaseProperty {
    val clientId: String
    val clientSecret: String
    val redirectUri: String
    val scope: String
}