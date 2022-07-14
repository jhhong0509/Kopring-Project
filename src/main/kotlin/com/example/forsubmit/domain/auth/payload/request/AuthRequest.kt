package com.example.forsubmit.domain.auth.payload.request

import javax.validation.constraints.NotBlank

class AuthRequest {
    @field:NotBlank
    lateinit var accountId: String
        private set

    @field:NotBlank
    lateinit var password: String
        private set
}