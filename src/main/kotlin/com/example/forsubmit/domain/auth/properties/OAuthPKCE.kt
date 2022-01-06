package com.example.forsubmit.domain.auth.properties

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*

interface OAuthPKCE {
    fun generateCodeVerifier(): String {
        val secureRandom = SecureRandom()
        val codeVerifier = ByteArray(32)
        secureRandom.nextBytes(codeVerifier)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier)
    }

    fun generateCodeChallenge(codeVerifier: String): String {
        val bytes = codeVerifier.toByteArray(charset("US-ASCII"))
        val messageDigest: MessageDigest = MessageDigest.getInstance("SHA-256")

        messageDigest.update(bytes, 0, bytes.size)
        val digest: ByteArray = messageDigest.digest()
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest)
    }
}