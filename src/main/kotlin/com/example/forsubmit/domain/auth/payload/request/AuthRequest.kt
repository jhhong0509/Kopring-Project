package com.example.forsubmit.domain.auth.payload.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class AuthRequest(
    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    val password: String
)