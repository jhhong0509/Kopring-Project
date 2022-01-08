package com.example.forsubmit.domain.auth.oauthparams

interface OAuthPKCE {
    val codeChallenge: String
    val codeChallengeMethod: String
}