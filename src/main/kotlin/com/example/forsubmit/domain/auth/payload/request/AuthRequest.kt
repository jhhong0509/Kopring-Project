package com.example.forsubmit.domain.auth.payload.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class AuthRequest {
    @field:Email
    @field:NotBlank
    lateinit var email: String
        private set

    @field:NotBlank
    lateinit var password: String
        private set
}