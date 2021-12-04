package com.example.forsubmit.domain.auth.payload.request

import javax.validation.constraints.NotBlank

class AuthRequest(
    @NotBlank
    val email: String,

    @NotBlank
    val password: String
)