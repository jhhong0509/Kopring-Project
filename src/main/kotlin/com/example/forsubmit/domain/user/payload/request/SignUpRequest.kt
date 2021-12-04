package com.example.forsubmit.domain.user.payload.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class SignUpRequest(
    @NotBlank
    val email: String,

    @NotBlank
    val name: String,

    @Size(min = 6, max = 20)
    val password: String,
)