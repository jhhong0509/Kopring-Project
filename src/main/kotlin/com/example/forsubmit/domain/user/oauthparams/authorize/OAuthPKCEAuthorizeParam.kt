package com.example.forsubmit.domain.auth.oauthparams.authorize

interface OAuthPKCEAuthorizeParam {
    val codeChallenge: String
    val codeChallengeMethod: String
}