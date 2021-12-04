package com.example.forsubmit.domain.auth.payload.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class AuthRequest(
    @NotBlank
    @Email
    val email: String,

    @NotBlank
    val password: String
)